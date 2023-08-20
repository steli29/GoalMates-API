package com.example.goalmates.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProgressDTO {
    private Long id;
    private Long postUpdatesId;
    private Long userId;
    private BigDecimal progress;
}
