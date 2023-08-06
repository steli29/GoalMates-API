package com.example.goalmates.controller;

import com.example.goalmates.dto.LikeDTO;
import com.example.goalmates.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;
    @PostMapping("")
    public void like(@RequestBody LikeDTO likeDTO) {
        likeService.addLike(likeDTO);
    }
    @DeleteMapping("")
    public void disLike(@RequestBody LikeDTO likeDTO) {
        try {
            likeService.disLike(likeDTO);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
