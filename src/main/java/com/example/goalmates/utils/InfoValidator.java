package com.example.goalmates.utils;

import com.example.goalmates.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class InfoValidator {
    public void passwordValidate(String password) {

        String passPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}";
        if (!password.matches(passPattern)) {
            throw new BadRequestException("Password not strong enough!");
        }
    }

    public void emailValidate(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(emailPattern)) {
            throw new BadRequestException("Enter a valid email!");
        }
    }

    public void firstNameValidate(String firstName) {
        if (firstName.trim().equals("")) {
            throw new BadRequestException("First name can not be empty!");
        }
    }

    public void lastNameValidate(String lastName) {
        if (lastName.trim().equals("")) {
            throw new BadRequestException("Last name can not be empty!");
        }
    }

    public void userNameValidate(String username) {
        if (username.trim().equals("")) {
            throw new BadRequestException("Enter username");
        }
    }
}
