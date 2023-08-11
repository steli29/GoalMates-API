package com.example.goalmates.controller;

import com.example.goalmates.dto.PostUpdatesDTO;
import com.example.goalmates.service.PostUpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public class PostUpdatesController {
    @Autowired
    private PostUpdatesService postUpdatesService;

    @PostMapping("")
    public void createUpdate(@RequestBody PostUpdatesDTO postUpdatesDTO) {
        postUpdatesService.addPostUpdate(postUpdatesDTO);
    }
}
