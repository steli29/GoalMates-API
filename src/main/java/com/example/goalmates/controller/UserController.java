package com.example.goalmates.controller;

import com.example.goalmates.config.JwtService;
import com.example.goalmates.dto.UserEditDTO;
import com.example.goalmates.dto.UserSearchResultDTO;
import com.example.goalmates.dto.UserWithoutPasswordDTO;
import com.example.goalmates.repository.UserRepository;
import com.example.goalmates.service.UserService;
import com.example.goalmates.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        UserWithoutPasswordDTO userWithoutPassword = mapper.map(user, UserWithoutPasswordDTO.class);
        return ResponseEntity.ok(userWithoutPassword);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<UserSearchResultDTO>> search(@PathVariable String name) {
        ModelMapper modelMapper = new ModelMapper();
        String[] s = name.split("=");
        List<User> users = userRepository.search(s[1]);
        System.out.println(users);
        List<UserSearchResultDTO> result = new ArrayList<>();
        for (User user : users) {
            result.add(modelMapper.map(user, UserSearchResultDTO.class));
        }
        return ResponseEntity.ok(result);
    }
}
