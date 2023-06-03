package com.example.goalmates.dto;

import com.example.goalmates.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEditDTO extends User {
    private String token;
}