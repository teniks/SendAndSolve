package su.sendandsolve.server.data.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import su.sendandsolve.server.data.domain.Tag;

import java.util.*;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    default Set<Tag> findAllByIds(Set<UUID> ids){
        Set<Tag> tags = new HashSet<>();
        for(UUID id : ids){
            tags.add(findById(id).orElseThrow(EntityNotFoundException::new));
        }
        return tags;
    }
}
