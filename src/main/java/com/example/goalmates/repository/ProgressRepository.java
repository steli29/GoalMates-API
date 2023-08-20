package com.example.goalmates.repository;

import com.example.goalmates.models.Comment;
import com.example.goalmates.models.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
}
