package su.sendandsolve.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import su.sendandsolve.server.data.domain.Session;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
}
