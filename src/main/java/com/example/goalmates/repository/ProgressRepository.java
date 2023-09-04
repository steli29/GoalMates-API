package com.example.goalmates.repository;

import com.example.goalmates.models.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    @Query("select p.progress from Progress p where p.postUpdates.id=:id")
    List<BigDecimal> findAllProgressByPostUpdatesId(@Param("id") Long progressId);
    @Query("select p from Progress p where p.user.id=:userId and p.postUpdates.id=:postUpdateId")
    Progress findByUserIdAndPostUpdateId(Long userId, Long postUpdateId);
}
