package com.example.goalmates.controller;

import com.example.goalmates.config.JwtService;
import com.example.goalmates.dto.UserEditDTO;
import com.example.goalmates.dto.UserSearchResultDTO;
import com.example.goalmates.dto.UserWithoutPasswordDTO;
import com.example.goalmates.repository.UserRepository;
import com.example.goalmates.service.UserService;
import com.example.goalmates.models.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserWithoutPasswordDTO> getUserById(@RequestParam("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        ModelMapper modelMapper = new ModelMapper();
        UserWithoutPasswordDTO searchResult = new UserWithoutPasswordDTO();
        if (user.isPresent()) {
            searchResult = modelMapper.map(user.get(), UserWithoutPasswordDTO.class);
        }
        return ResponseEntity.ok(searchResult);
    }
}
