package su.sendandsolve.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import su.sendandsolve.server.data.domain.Task;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
