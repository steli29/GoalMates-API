package com.example.goalmates.service;

import com.example.goalmates.dto.PostUpdatesDTO;
import com.example.goalmates.exception.BadRequestException;
import com.example.goalmates.models.Post;
import com.example.goalmates.models.PostUpdates;
import com.example.goalmates.models.User;
import com.example.goalmates.repository.PostRepository;
import com.example.goalmates.repository.PostUpdatesRepository;
import com.example.goalmates.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addPostUpdate(PostUpdatesDTO postUpdatesDTO) {

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

        postUpdatesRepository.save(postUpdates);
    }

    public List<PostUpdatesDTO> getAllUpdatesByPostId(Long id){
        List<PostUpdatesDTO> updatesDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        List<PostUpdates> u = postUpdatesRepository.findAllByPostId(id);
        if (u.isEmpty()) {
            throw new BadRequestException("Updates not found");
        }
        u.forEach(up->{
            updatesDTOS.add(modelMapper.map(up,PostUpdatesDTO.class));
        });
        return updatesDTOS;
    }
}
