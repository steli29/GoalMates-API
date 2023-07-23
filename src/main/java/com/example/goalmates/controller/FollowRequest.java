package com.example.goalmates.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//dto
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequest {
    private Long followeeId;
    private Long followerId;
}
