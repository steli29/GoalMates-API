package com.example.goalmates.service;

import com.example.goalmates.dto.CreatePostDTO;
import com.example.goalmates.dto.EditPostDTO;
import com.example.goalmates.exception.BadRequestException;
import com.example.goalmates.models.Post;
import com.example.goalmates.models.User;
import com.example.goalmates.repository.PostRepository;
import com.example.goalmates.repository.PostUserRepository;
import com.example.goalmates.repository.UserRepository;
import com.example.goalmates.utils.EmailUtil;
import com.example.goalmates.utils.InfoValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    public void createPost(CreatePostDTO createPostDTO) {
        ModelMapper modelMapper = new ModelMapper();
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
        postRepository.save(post);
        newUsers.forEach(email -> {
            emailUtil.sendInvitationMail(email, userRepository.findById(createPostDTO.getCreatedBy()).get().getEmail());
        });
    }

    public List<Post> getAllPostsByUserCreated(Long id){
        List<Post> posts = postRepository.findAllPostsByCreatedBy(id);
        return posts;
    }

    public Post getPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()){
            throw new BadRequestException("Post not found");
        }
        return post.get();
    }
    public void deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()){
            throw new BadRequestException("Post not found");
        }
        postRepository.deleteById(id);
    }
    public Post updatePost(EditPostDTO editPostDTO){
        Optional<Post> p = postRepository.findById(editPostDTO.getId());
        if (p.isEmpty()){
            throw new BadRequestException("Post not found");
        }
        Post post = p.get();
        post.setTitle(editPostDTO.getTitle());
        post.setContent(editPostDTO.getContent());

        postRepository.save(post);
        return post;
    }

// todo get all posts shared with user
}
