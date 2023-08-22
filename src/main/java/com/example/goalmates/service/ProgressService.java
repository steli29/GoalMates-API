package com.example.goalmates.service;

import com.example.goalmates.dto.ProgressDTO;
import com.example.goalmates.exception.BadRequestException;
import com.example.goalmates.models.PostUpdates;
import com.example.goalmates.models.Progress;
import com.example.goalmates.models.User;
import com.example.goalmates.repository.PostUpdatesRepository;
import com.example.goalmates.repository.ProgressRepository;
import com.example.goalmates.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressService {
    @Autowired
    private ProgressRepository progressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostUpdatesRepository postUpdatesRepository;

    public void addProgress(ProgressDTO progressDTO) {
        Optional<User> user = userRepository.findById(progressDTO.getUserId());
        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        Optional<PostUpdates> postUpdates = postUpdatesRepository.findById(progressDTO.getPostUpdatesId());
        if (postUpdates.isEmpty()) {
            throw new BadRequestException("Update not found");
        }
        Progress progress = new Progress();
        progress.setUser(user.get());
        progress.setPostUpdates(postUpdates.get());
        progress.setProgress(progressDTO.getProgress());
        progress.setIsRated(true);
        progressRepository.save(progress);
    }

    private void updateTotalProgress() {

    }
}
