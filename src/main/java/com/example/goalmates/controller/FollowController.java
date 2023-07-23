package com.example.goalmates.controller;

import com.example.goalmates.dto.UserSearchResultDTO;
import com.example.goalmates.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("")
    public ResponseEntity<String> followUser(@RequestBody FollowRequest followRequest) {
        followService.followUser(followRequest.getFollowerId(), followRequest.getFolloweeId());
        return ResponseEntity.ok("Successfully followed user");
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody FollowRequest followRequest) {
        followService.unfollowUser(followRequest.getFollowerId(), followRequest.getFolloweeId());
        return ResponseEntity.ok("Successfully unfollowed user");
    }
    @GetMapping("/followed")
    public ResponseEntity<List<UserSearchResultDTO>> allFollowed(@RequestParam("followeeId") Long followeeId){
       return ResponseEntity.ok(followService.followed(followeeId));
    }
    @GetMapping("/following")
    public ResponseEntity<List<UserSearchResultDTO>> allFollowing(@RequestParam("followerId") Long followerId){
        return ResponseEntity.ok(followService.following(followerId));
    }
}
