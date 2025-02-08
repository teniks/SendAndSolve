package su.sendandsolve.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import su.sendandsolve.server.data.domain.Note;

import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
}
