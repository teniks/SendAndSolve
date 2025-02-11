package su.sendandsolve.server.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import su.sendandsolve.server.data.domain.Task;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query("SELECT t FROM Task t JOIN FETCH t.creator  WHERE t.isDeleted = false")
    @Transactional(readOnly = true)
    Page<Task> findAllWithCreator(Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.isDeleted = :isDeleted")
    @Transactional(readOnly = true)
    Page<Task> findAllByDeletedStatus(@Param("isDeleted") boolean isDeleted, Pageable pageable);

    @Query("SELECT t FROM Task t " +
            "LEFT JOIN FETCH t.tags tag " +
            "WHERE t.isDeleted = false " +
            "AND (:tagIds IS NULL OR tag.uuid IN :tagIds) " +
            "GROUP BY t " +
            "HAVING COUNT(DISTINCT tag.uuid) = :tagCount")
    @Transactional(readOnly = true)
    Page<Task> findAllByTags(
            @Param("tagIds") List<UUID> tagIds,
            @Param("tagCount") Long tagCount,
            Pageable pageable
    );

    @Modifying
    @Query(nativeQuery = true, value = """
        INSERT INTO TaskResource (id_task, id_resource)
        SELECT :taskId, r.uuid
        FROM Resources r
        WHERE r.uuid IN :resourcesIds
          AND NOT EXISTS (
              SELECT 1
              FROM TaskResource tr
              WHERE tr.id_task = :taskId AND tr.id_resource = r.uuid
          );
    """)
    @Transactional
    List<Task> addResourcesToTask(@Param("taskId") UUID taskId, @Param("resourcesIds") Set<UUID> resourcesIds);
}
