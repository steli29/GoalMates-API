package com.example.goalmates.service;

import com.example.goalmates.dto.LikeDTO;
import com.example.goalmates.exception.BadRequestException;
import com.example.goalmates.models.Comment;
import com.example.goalmates.models.Like;
import com.example.goalmates.models.User;
import com.example.goalmates.repository.CommentRepository;
import com.example.goalmates.repository.LikeRepository;
import com.example.goalmates.repository.PostRepository;
import com.example.goalmates.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;
    public void addLike(LikeDTO likeDTO){
        Optional<User> user = userRepository.findById(likeDTO.getUserId());
        if (user.isEmpty()){
            throw new BadRequestException("User not found");
        }
        Optional<Comment> comment = commentRepository.findById(likeDTO.getCommentId());
        if (comment.isEmpty()){
            throw  new BadRequestException("Comment not found");
        }
        Like like = new Like();
        like.setComment(comment.get());
        like.setUser(user.get());
        // todo validation
        likeRepository.save(like);
    }
    public void disLike(LikeDTO likeDTO){
        Optional<User> user = userRepository.findById(likeDTO.getUserId());
        if (user.isEmpty()){
            throw new BadRequestException("User not found");
        }
        Optional<Comment> comment = commentRepository.findById(likeDTO.getCommentId());
        if (comment.isEmpty()){
            throw  new BadRequestException("Comment not found");
        }
        Like like = likeRepository.findByCommentAndUser(comment.get(), user.get());

        likeRepository.delete(like);
    }
}
