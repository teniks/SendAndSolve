package su.sendandsolve.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import su.sendandsolve.server.data.domain.Tag;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}
