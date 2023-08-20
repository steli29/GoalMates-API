package com.example.goalmates.controller;

import com.example.goalmates.dto.ProgressDTO;
import com.example.goalmates.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/progress")
public class ProgressController {
    @Autowired
    private ProgressService progressService;

    @PostMapping("")
    public void addProgress(@RequestBody ProgressDTO progressDTO) {
        progressService.addProgress(progressDTO);
    }
}
