package com.example.goalmates.dto;

import com.example.goalmates.models.Comment;
import com.example.goalmates.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private UserWithoutPasswordDTO createdBy;
    private List<Comment> comments = new ArrayList<>();
    private Date dateCreated;
}
