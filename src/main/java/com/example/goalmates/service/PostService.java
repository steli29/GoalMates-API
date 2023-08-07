package com.example.goalmates.service;

import com.example.goalmates.dto.*;
import com.example.goalmates.exception.BadRequestException;
import com.example.goalmates.models.Comment;
import com.example.goalmates.models.Post;
import com.example.goalmates.models.PostUsers;
import com.example.goalmates.models.User;
import com.example.goalmates.repository.CommentRepository;
import com.example.goalmates.repository.PostRepository;
import com.example.goalmates.repository.PostUserRepository;
import com.example.goalmates.repository.UserRepository;
import com.example.goalmates.utils.EmailUtil;
import com.example.goalmates.utils.InfoValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private InfoValidator infoValidator;
    @Autowired
    private PostUserRepository postUserRepository;
    @Autowired
    private CommentRepository commentRepository;

    public void createPost(CreatePostDTO createPostDTO) {
        ModelMapper modelMapper = new ModelMapper();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = modelMapper.map(createPostDTO, Post.class);
        List<User> registeredUsers = new ArrayList<>();
        List<String> newUsers = new ArrayList<>();
        createPostDTO.getSharedWithUsers()
                .forEach(email -> {
                    infoValidator.emailValidate(email);
                    if (userRepository.findByEmail(email).isPresent()) {
                        registeredUsers.add(userRepository.findByEmail(email).get());
                    } else {
                        newUsers.add(email);
                    }
                });
        post.setSharedWithUsers(registeredUsers);
        post.setCreatedBy(user);
        post.setDateCreated(new Date());
        post.setComments(0L);
        postRepository.save(post);
        newUsers.forEach(email -> {
            emailUtil.sendInvitationMail(email, userRepository.findById(createPostDTO.getCreatedBy()).get().getEmail());
        });
    }

    public List<PostDTO> getAllPostsByUserCreated(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        List<PostDTO> postDTO = new ArrayList<>();
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        List<Post> posts = postRepository.findAllPostsByCreatedBy(user.get());

        posts.forEach(post -> {
            postDTO.add(modelMapper.map(post, PostDTO.class));
        });
        return postDTO;
    }

    public PostDTO getPost(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new BadRequestException("Post not found");
        }
        return modelMapper.map(post.get(), PostDTO.class);
    }
    public void deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()){
            throw new BadRequestException("Post not found");
        }
        postRepository.deleteById(id);
    }
    public PostDTO updatePost(EditPostDTO editPostDTO){
        ModelMapper modelMapper = new ModelMapper();
        Optional<Post> p = postRepository.findById(editPostDTO.getId());
        if (p.isEmpty()){
            throw new BadRequestException("Post not found");
        }
        Post post = p.get();
        post.setTitle(editPostDTO.getTitle());
        post.setContent(editPostDTO.getContent());

        postRepository.save(post);
        return modelMapper.map(post,PostDTO.class);
    }
    public List<PostDTO> getFeed (Long id){
        List<Long> allPostIds = postRepository.findAllIds();
        List<PostDTO> feed = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        allPostIds.forEach(p->{
            PostUsers u = new PostUsers();
            u.setPost(postRepository.findById(p).get());
            u.setUser(userRepository.findById(id).get());
            if (postUserRepository.findById(u).isPresent()){
                feed.add(modelMapper.map(postRepository.findById(p).get(),PostDTO.class));
            }
        });
        return feed;
    }

    public List<CommentDTO> getAllCommentsByPost(Long postId){
        List<Comment> comments =commentRepository.findAllCommentsByPostId(postId);
        System.out.println(comments.get(0).getLikes());
        List<CommentDTO> commentDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        comments.forEach(comment->{
            commentDTOS.add(modelMapper.map(comment, CommentDTO.class));
        });
        return commentDTOS;
    }
}
