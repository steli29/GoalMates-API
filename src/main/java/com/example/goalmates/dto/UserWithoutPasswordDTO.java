package com.example.goalmates.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserWithoutPasswordDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
