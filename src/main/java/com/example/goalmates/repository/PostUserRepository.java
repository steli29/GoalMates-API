package com.example.goalmates.repository;

import com.example.goalmates.models.PostUsers;
import com.example.goalmates.models.PostUsersCompKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostUserRepository  extends JpaRepository<PostUsersCompKey,PostUsers> {
}
