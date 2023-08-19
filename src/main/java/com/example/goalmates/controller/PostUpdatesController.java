package com.example.goalmates.controller;

import com.example.goalmates.dto.PostUpdatesDTO;
import com.example.goalmates.service.PostUpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/update")
public class PostUpdatesController {
    @Autowired
    private PostUpdatesService postUpdatesService;

    @PostMapping("")
    public void createUpdate(@RequestBody PostUpdatesDTO postUpdatesDTO) {
        postUpdatesService.addPostUpdate(postUpdatesDTO);
    }
    @GetMapping("/findAll/")
    public ResponseEntity<List<PostUpdatesDTO>> getAllPostsByUsedId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(postUpdatesService.getAllUpdatesByPostId(id));
    }
}
