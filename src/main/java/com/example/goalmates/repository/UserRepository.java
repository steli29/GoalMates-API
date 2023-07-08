package com.example.goalmates.repository;

import com.example.goalmates.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE " +
            "u.firstName LIKE CONCAT('%',:name, '%')" +
            "Or u.lastName LIKE CONCAT('%', :name, '%')" +
            "Or u.fullName LIKE CONCAT('%', :name, '%')")
    List<User> search(String name);

}
