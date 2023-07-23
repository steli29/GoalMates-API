package com.example.goalmates.service;

import com.example.goalmates.dto.UserSearchResultDTO;
import com.example.goalmates.exception.BadRequestException;
import com.example.goalmates.models.Follow;
import com.example.goalmates.models.User;
import com.example.goalmates.repository.FollowRepository;
import com.example.goalmates.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    public FollowService(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }
    public void followUser(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found"));

        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new IllegalArgumentException("Followee not found"));
        if (followRepository.findByFollowerIdAndFolloweeId(follower.getId(), followee.getId()).isPresent()){
            throw new BadRequestException("You are already following this user");
        }else if (followerId.equals(followeeId)){
            throw new BadRequestException("You can not follow yourself");
        }
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowee(followee);
        follow.setDateCreated(new Date());

        followRepository.save(follow);
    }
    @Transactional
    public void unfollowUser(Long followerId, Long followeeId) {
        if (followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId).isPresent()){
            followRepository.deleteByFollowerIdAndFolloweeId(followerId, followeeId);
        }else {
            throw new BadRequestException("You are not following this user");
        }
    }

    public List<UserSearchResultDTO> followed (Long followeeId){
        ModelMapper modelMapper = new ModelMapper();
        List<UserSearchResultDTO> result = new ArrayList<>();
        List<Follow> followers = followRepository.findAllFollowerIdByFolloweeId(followeeId);
        followers.forEach(follower->{
            result.add(modelMapper.map(follower.getFollower(), UserSearchResultDTO.class));
        });
        return result;
    }
    public List<UserSearchResultDTO> following (Long followerId){
        ModelMapper modelMapper = new ModelMapper();
        List<UserSearchResultDTO> result = new ArrayList<>();
        List<Follow> followings = followRepository.findAllFolloweeIdByFollowerId(followerId);
        followings.forEach(follower->{
            result.add(modelMapper.map(follower.getFollowee(), UserSearchResultDTO.class));
        });
        return result;
    }

    public boolean isFollowing (Long followeeId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Follow> following = followRepository.findByFollowerIdAndFolloweeId(user.getId(), followeeId);
        return following.isPresent();
    }
}
