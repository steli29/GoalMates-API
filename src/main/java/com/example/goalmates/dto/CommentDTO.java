package com.example.goalmates.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String text;
    private PostDTO post;
    private UserWithoutPasswordDTO user;
    private List<LikeDTO> likes = new ArrayList<>();
}
