package su.sendandsolve.server.data.repository;

import org.springframework.stereotype.Repository;
import su.sendandsolve.server.data.domain.Resource;

import java.util.UUID;

@Repository
public interface ResourceRepository extends BaseRepository<Resource, UUID> {
}
