package com.example.goalmates.dto;

import com.example.goalmates.models.Like;
import com.example.goalmates.models.Post;
import com.example.goalmates.models.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private String text;
    private PostDTO post;
    private UserWithoutPasswordDTO user;
    private List<LikeDTO> likes = new ArrayList<>();
}
