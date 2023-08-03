package com.example.goalmates.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_shared_with_users")
public class PostUsersCompKey {
    @EmbeddedId
    private PostUsers id;
}
