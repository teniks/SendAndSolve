package su.sendandsolve.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import su.sendandsolve.server.data.domain.Team;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
}
