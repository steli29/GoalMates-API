package com.example.goalmates.controller;

import com.example.goalmates.dto.ProgressDTO;
import com.example.goalmates.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progress")
public class ProgressController {
    @Autowired
    private ProgressService progressService;

    @PostMapping("")
    public void addProgress(@RequestBody ProgressDTO progressDTO) {
        progressService.addProgress(progressDTO);
    }

    @GetMapping("/")
    public ResponseEntity<ProgressDTO> getProgress(@RequestParam("userId") Long userId, @RequestParam("postUpdateId") Long postUpdateId) {
       return ResponseEntity.ok(progressService.getProgress(userId, postUpdateId));
    }
}
