package su.sendandsolve.server.data.repository;

import org.springframework.stereotype.Repository;
import su.sendandsolve.server.data.domain.Tag;

import java.util.UUID;

@Repository
public interface TagRepository extends BaseRepository<Tag, UUID> {
}
