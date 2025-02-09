package su.sendandsolve.server.data.repository;

import org.springframework.stereotype.Repository;
import su.sendandsolve.server.data.domain.Session;

import java.util.UUID;

@Repository
public interface SessionRepository extends BaseRepository<Session, UUID> {
}
