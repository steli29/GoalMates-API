package com.example.goalmates.service;

import com.example.goalmates.dto.PostUpdatesDTO;
import com.example.goalmates.exception.BadRequestException;
import com.example.goalmates.models.Post;
import com.example.goalmates.models.PostUpdates;
import com.example.goalmates.models.Progress;
import com.example.goalmates.models.User;
import com.example.goalmates.repository.PostRepository;
import com.example.goalmates.repository.PostUpdatesRepository;
import com.example.goalmates.repository.ProgressRepository;
import com.example.goalmates.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostUpdatesService {
    @Autowired
    private PostUpdatesRepository postUpdatesRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProgressRepository progressRepository;
    @Autowired
    private ImageService imageService;

    public void addPostUpdate(PostUpdatesDTO postUpdatesDTO, MultipartFile file) throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Post> post = postRepository.findById(postUpdatesDTO.getPostId());
        if (post.isEmpty()) {
            throw new BadRequestException("Post not found");
        }
        Optional<User> user = userRepository.findById(postUpdatesDTO.getUserId());
        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        PostUpdates postUpdates = new PostUpdates();
        postUpdates.setPost(post.get());
        postUpdates.setUser(user.get());
        postUpdates.setText(postUpdatesDTO.getText());
        postUpdates.setTitle(postUpdatesDTO.getTitle());
        postUpdates.setImage(postUpdatesDTO.getImage());
        postUpdates.setTotalProgress(BigDecimal.ZERO);
        postUpdatesRepository.save(postUpdates);
        imageService.uploadUpdateImage(file, postUpdates.getId());
        List<User> users = new ArrayList<>();
        post.get().getSharedWithUsers().forEach(u -> {
            if (userRepository.findById(u.getId()).isPresent()) {
                users.add(u);
            }
        });
        users.forEach(u -> {
            Progress p = new Progress();
            p.setPostUpdates(postUpdates);
            p.setUser(u);
            p.setIsRated(false);
            p.setProgress(BigDecimal.valueOf(0));
            progressRepository.save(p);
        });
    }

    public List<PostUpdatesDTO> getAllUpdatesByPostId(Long id) {
        List<PostUpdatesDTO> updatesDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        List<PostUpdates> u = postUpdatesRepository.findAllByPostId(id);
        u.forEach(up -> {
            updatesDTOS.add(modelMapper.map(up, PostUpdatesDTO.class));
        });
        return updatesDTOS;
    }
}
