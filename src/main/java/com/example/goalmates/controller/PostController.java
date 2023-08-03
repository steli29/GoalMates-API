package com.example.goalmates.controller;

import com.example.goalmates.dto.CreatePostDTO;
import com.example.goalmates.dto.EditPostDTO;
import com.example.goalmates.models.Post;
import com.example.goalmates.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("")
    public void createPost(@RequestBody CreatePostDTO createPostDTO) {
        postService.createPost(createPostDTO);
    }

    @GetMapping("/findAll/")
    public ResponseEntity<List<Post>> getAllPostsByUsedId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(postService.getAllPostsByUserCreated(id));
    }

    @GetMapping("/")
    public ResponseEntity<Post> getPostById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @DeleteMapping("/")
    public void deletePost(@RequestParam("id") Long id) {
        postService.deletePost(id);
    }

    @PutMapping("/update")
    public Post updatePost(@RequestBody EditPostDTO editPostDTO){
        return postService.updatePost(editPostDTO);
    }
}
