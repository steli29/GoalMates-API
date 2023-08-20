package com.example.goalmates.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "progress")
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "post_updates_id")
    private PostUpdates postUpdates;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal progress;
    private Boolean isRated;
}
