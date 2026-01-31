package org.example.userservice.reposetories;

import org.example.userservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session,Long> {
    Session save(Session session);
}
