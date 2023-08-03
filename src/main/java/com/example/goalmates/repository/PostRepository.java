package com.example.goalmates.repository;

import com.example.goalmates.models.Follow;
import com.example.goalmates.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllPostsByCreatedBy(Long id);
    @Query("select id from Post")
    List<Long> findAllIds();
}
