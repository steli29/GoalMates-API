package com.example.goalmates.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserFollowingDTO extends UserSearchResultDTO {
    private Boolean isFollowing;
    private String email;
}
