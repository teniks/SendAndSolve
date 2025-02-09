package su.sendandsolve.server.data.repository;

import org.springframework.stereotype.Repository;
import su.sendandsolve.server.data.domain.Note;

import java.util.UUID;

@Repository
public interface NoteRepository extends BaseRepository<Note, UUID> {
}
