package su.sendandsolve.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import su.sendandsolve.server.data.domain.Resource;

import java.util.UUID;

public interface ResourceRepository extends JpaRepository<Resource, UUID> {
}
