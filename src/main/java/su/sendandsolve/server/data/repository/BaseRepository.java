package su.sendandsolve.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<E, ID> extends JpaRepository<E, ID> {
}

