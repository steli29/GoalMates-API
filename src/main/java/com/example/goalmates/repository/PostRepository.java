package com.example.goalmates.repository;

import com.example.goalmates.models.Follow;
import com.example.goalmates.models.Post;
import com.example.goalmates.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllPostsByCreatedBy(User id);
    @Query("select id from Post")
    List<Long> findAllIds();
}
