package org.example.userservice.reposetories;

import org.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReposetories extends JpaRepository<User,Long> {
    User save(User user);
    Optional<User> findByEmail(String email);
}
