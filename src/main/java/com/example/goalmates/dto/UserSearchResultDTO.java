package com.example.goalmates.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchResultDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String image;
}
