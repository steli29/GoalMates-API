package com.example.goalmates.repository;

import com.example.goalmates.models.PostUsers;
import com.example.goalmates.models.PostUsersCompKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostUserRepository  extends JpaRepository<PostUsers, PostUsersCompKey> {
}
