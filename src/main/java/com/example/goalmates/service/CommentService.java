package com.example.goalmates.service;

import com.example.goalmates.dto.PostUserIdDTO;
import com.example.goalmates.exception.BadRequestException;
import com.example.goalmates.models.Comment;
import com.example.goalmates.models.Post;
import com.example.goalmates.models.User;
import com.example.goalmates.repository.CommentRepository;
import com.example.goalmates.repository.PostRepository;
import com.example.goalmates.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    public void addComment(PostUserIdDTO postUserIdDTO) {
        Optional<Post> post = postRepository.findById(postUserIdDTO.getPostId());
        if (post.isEmpty()){
            throw new BadRequestException("Post not found");
        }
        Optional<User> user = userRepository.findById(postUserIdDTO.getUserId());
        if (user.isEmpty()){
            throw new BadRequestException("User not found");
        }
        Comment comment = new Comment();
        comment.setPost(post.get());
        comment.setUser(user.get());
        comment.setText(postUserIdDTO.getText());
        commentRepository.save(comment);
        Post p = post.get();
        p.setCommentsCount(p.getCommentsCount()+1);
        postRepository.save(p);
    }

    public void deleteComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()){
            throw new BadRequestException("Comment not found");
        }
        Optional<Post> post = postRepository.findById(comment.get().getPost().getId());
        if (post.isEmpty()){
            throw new BadRequestException("Post not found");
        }
        Post p = post.get();
        p.setCommentsCount(p.getCommentsCount()-1);
        postRepository.save(p);
        commentRepository.delete(comment.get());
    }
}
