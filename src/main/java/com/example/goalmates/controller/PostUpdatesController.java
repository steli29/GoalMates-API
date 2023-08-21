package com.example.goalmates.controller;

import com.example.goalmates.dto.PostUpdatesDTO;
import com.example.goalmates.service.ImageService;
import com.example.goalmates.service.PostUpdatesService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void createUpdate(@RequestParam(name = "file") MultipartFile image, @RequestParam(name = "dto") String dto) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PostUpdatesDTO postUpdatesDTO = mapper.readValue(dto, PostUpdatesDTO.class);
        postUpdatesService.addPostUpdate(postUpdatesDTO, image);
    }

    @GetMapping("/findAll/")
    public ResponseEntity<List<PostUpdatesDTO>> getAllPostsByUsedId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(postUpdatesService.getAllUpdatesByPostId(id));
    }

}
