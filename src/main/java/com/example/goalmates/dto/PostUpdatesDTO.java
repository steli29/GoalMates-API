package com.example.goalmates.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PostUpdatesDTO {
    private Long id;
    private String text;
    private Long postId;
    private Long userId;
    private String title;
    private byte[] image;
    private BigDecimal totalProgress;
}
