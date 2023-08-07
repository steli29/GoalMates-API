package com.example.goalmates.repository;

import com.example.goalmates.dto.LikeDTO;
import com.example.goalmates.models.Comment;
import com.example.goalmates.models.Like;
import com.example.goalmates.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("select l from Like l where l.comment.id=:comment and l.user.id=:user")
    Like findByCommentIdAndUserId(Long comment, Long user);

    Like findByCommentAndUser(Comment comment, User user);

    List<Like> findAllLikesByCommentId(Long id);
}
