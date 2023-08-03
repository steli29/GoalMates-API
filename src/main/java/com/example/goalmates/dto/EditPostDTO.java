package com.example.goalmates.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditPostDTO {
    private Long id;
    private String title;
    private String content;
}
