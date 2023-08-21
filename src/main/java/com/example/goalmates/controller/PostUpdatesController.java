package com.example.goalmates.controller;

import com.example.goalmates.dto.PostUpdatesDTO;
import com.example.goalmates.service.ImageService;
import com.example.goalmates.service.PostUpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/update")
public class PostUpdatesController {
    @Autowired
    private PostUpdatesService postUpdatesService;
    @Autowired
    private ImageService imageService;

    @PostMapping("")
    public void createUpdate(@RequestBody PostUpdatesDTO postUpdatesDTO) {
        postUpdatesService.addPostUpdate(postUpdatesDTO);
    }

    @GetMapping("/findAll/")
    public ResponseEntity<List<PostUpdatesDTO>> getAllPostsByUsedId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(postUpdatesService.getAllUpdatesByPostId(id));
    }

    @PostMapping("/image/")
    public void uploadImage(@RequestParam(name = "file") MultipartFile image, @RequestParam("id") Long id) throws IOException {
        imageService.uploadUpdateImage(image, id);
    }
}
