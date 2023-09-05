package com.example.goalmates.dto;

import com.example.goalmates.models.Comment;
import com.example.goalmates.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
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
    private Long commentsCount;
    private Date dateCreated;
    private BigDecimal progress;
}
