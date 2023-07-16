package com.example.goalmates.repository;

import com.example.goalmates.models.Follow;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    void deleteByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    Optional<Follow> findByFollowerIdAndFolloweeId(Long id, Long id1);

    List<Follow> findAllFollowerIdByFolloweeId(Long followeeId);

    List<Follow> findAllFolloweeIdByFollowerId(Long followerId);
}
