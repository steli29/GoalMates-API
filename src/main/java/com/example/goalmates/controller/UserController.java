package com.example.goalmates.controller;

import com.example.goalmates.config.JwtService;
import com.example.goalmates.dto.UserEditDTO;
import com.example.goalmates.dto.UserFollowingDTO;
import com.example.goalmates.dto.UserSearchResultDTO;
import com.example.goalmates.dto.UserWithoutPasswordDTO;
import com.example.goalmates.repository.UserRepository;
import com.example.goalmates.service.FollowService;
import com.example.goalmates.service.ImageService;
import com.example.goalmates.service.UserService;
import com.example.goalmates.models.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private FollowService followService;
    @Autowired
    private ImageService imageService;


    @PostMapping("/edit")
    public ResponseEntity<UserWithoutPasswordDTO> edit(@RequestBody UserEditDTO userEditDTO) {
        ModelMapper mapper = new ModelMapper();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.edit(user, userEditDTO);
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        UserWithoutPasswordDTO userWithoutPassword = mapper.map(user, UserWithoutPasswordDTO.class);
        userWithoutPassword.setToken(jwtToken);
        return ResponseEntity.ok(userWithoutPassword);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserSearchResultDTO>> search(@RequestParam("name") String name) {
        ModelMapper modelMapper = new ModelMapper();
        List<User> users = userRepository.search(name);
        List<UserSearchResultDTO> result = new ArrayList<>();
        for (User user : users) {
            result.add(modelMapper.map(user, UserSearchResultDTO.class));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<UserFollowingDTO> getUserById(@RequestParam("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        ModelMapper modelMapper = new ModelMapper();
        UserFollowingDTO searchResultDTO = new UserFollowingDTO();
        if (user.isPresent()) {
            searchResultDTO = modelMapper.map(user.get(), UserFollowingDTO.class);
        }
        searchResultDTO.setIsFollowing(followService.isFollowing(id));
        return ResponseEntity.ok(searchResultDTO);
    }
    @PostMapping("/image")
    public void uploadImage(@RequestParam(name = "file") MultipartFile image) throws IOException {
        imageService.uploadImage(image);
    }

    @GetMapping("/image")
    public void downloadImage(@RequestParam(name = "file") String name, HttpServletResponse response) throws IOException {
        imageService.downloadImage(name, response);
    }
}
