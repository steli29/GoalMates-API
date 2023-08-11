package com.example.goalmates.repository;

import com.example.goalmates.models.PostUpdates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostUpdatesRepository extends JpaRepository<PostUpdates, Long> {
}
