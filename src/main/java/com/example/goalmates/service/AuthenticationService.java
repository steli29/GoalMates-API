package com.example.goalmates.service;

import com.example.goalmates.config.JwtService;
import com.example.goalmates.controller.AuthenticationRequest;
import com.example.goalmates.controller.RegisterRequest;
import com.example.goalmates.dto.UserWithoutPasswordDTO;
import com.example.goalmates.dto.VerifyDTO;
import com.example.goalmates.exception.BadRequestException;
import com.example.goalmates.exception.NotFoundException;
import com.example.goalmates.exception.UnauthorizedException;
import com.example.goalmates.repository.UserRepository;
import com.example.goalmates.user.Role;
import com.example.goalmates.models.User;
import com.example.goalmates.utils.EmailUtil;
import com.example.goalmates.utils.InfoValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final InfoValidator validator;
    private final EmailUtil emailUtil;

    public UserWithoutPasswordDTO register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("User with this email already exists");
        }
        validator.emailValidate(request.getEmail());
        validator.passwordValidate(request.getPassword());
        validator.firstNameValidate(request.getFirstName());
        validator.lastNameValidate(request.getLastName());
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fullName(request.getFirstName()+ " "+ request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        String jwtToken = jwtService.generateToken(user);
        ModelMapper modelMapper = new ModelMapper();
        UserWithoutPasswordDTO userWithoutPassword = modelMapper.map(user,UserWithoutPasswordDTO.class);
        userWithoutPassword.setToken(jwtToken);
        Random rand = new Random();
        Long digit = (long) rand.nextInt(1000000);
        String formattedCode = String.format("%06d", digit);
        user.setRegistrationCode(Integer.valueOf(formattedCode));
        user.setVerified(false);
        emailUtil.sendMailWithCode(userWithoutPassword.getEmail(), String.valueOf(user.getRegistrationCode()));
        repository.save(user);
        return userWithoutPassword;
    }

    public UserWithoutPasswordDTO login(AuthenticationRequest request) {
        if (!repository.findByEmail(request.getEmail()).get().isVerified()){
         throw new BadRequestException("User is not verified");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new BadRequestException("Email is mandatory");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BadRequestException("Password is mandatory");
        }
        validator.emailValidate(request.getEmail());
        validator.passwordValidate(request.getPassword());
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            User user = repository.findByEmail(request.getEmail()).get();
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new UnauthorizedException("Wrong credentials");
            }
            String jwtToken = jwtService.generateToken(user);
            ModelMapper modelMapper = new ModelMapper();
            UserWithoutPasswordDTO userWithoutPassword = modelMapper.map(user,UserWithoutPasswordDTO.class);
            userWithoutPassword.setToken(jwtToken);
            return userWithoutPassword;
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public UserWithoutPasswordDTO verify(VerifyDTO verifyDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<User> u = repository.findByEmail(verifyDTO.getEmail());
        if (u.isEmpty()){
            throw new NotFoundException("User not found");
        }

        User user = u.get();
        if (!verifyDTO.getEmail().equals(user.getEmail())
        || !verifyDTO.getCode().equals(user.getRegistrationCode())){
            throw new BadRequestException("Code does not match the user");
        }
        user.setVerified(true);
        repository.save(user);

        return modelMapper.map(user,UserWithoutPasswordDTO.class);
    }
}
