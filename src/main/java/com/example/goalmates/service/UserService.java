package com.example.goalmates.service;

import com.example.goalmates.dto.UserEditDTO;
import com.example.goalmates.exception.BadRequestException;
import com.example.goalmates.repository.UserRepository;
import com.example.goalmates.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private InfoValidator infoValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public void edit(User user, UserEditDTO userEditDTO) {
        if (userEditDTO.getEmail() != null && userRepository.findByEmail(userEditDTO.getEmail()).isEmpty()){
            infoValidator.emailValidate(userEditDTO.getEmail());
            user.setEmail(userEditDTO.getEmail());
        }else {
            throw new BadRequestException("Email is already used");
        }


        if (userEditDTO.getFirstName() != null) {
            infoValidator.firstNameValidate(userEditDTO.getFirstName());
            user.setFirstName(userEditDTO.getFirstName());
        }
        if (userEditDTO.getLastName() != null) {
            infoValidator.lastNameValidate(userEditDTO.getLastName());
            user.setLastName(userEditDTO.getLastName());
        }

        if (userEditDTO.getPassword() != null) {
            infoValidator.passwordValidate(userEditDTO.getPassword());
            user.setPassword(passwordEncoder.encode(userEditDTO.getPassword()));
        }
    }
}
