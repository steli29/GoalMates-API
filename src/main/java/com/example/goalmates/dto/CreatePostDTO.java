package com.example.goalmates.dto;

import com.example.goalmates.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostDTO {
    private Long id;
    private String title;
    private String content;
    private Long createdBy;
    private List<String > sharedWithUsers;
}
