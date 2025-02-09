package su.sendandsolve.server.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import su.sendandsolve.server.data.domain.Resource;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.domain.Task;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TaskRepository extends BaseRepository<Task, UUID> {
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

    @Query("SELECT t FROM Task t" +
            "LEFT JOIN FETCH t.tags tag " +
            "WHERE t.isDeleted = false"
    )
    @Transactional(readOnly = true)
    Iterable<Tag> getTags(@Param("taskId") UUID taskId);

    @Query("SELECT t FROM Task t" +
            "LEFT JOIN FETCH t.resources resources " +
            "WHERE t.isDeleted = false")
    @Transactional(readOnly = true)
    Iterable<Resource> getResources(@Param("taskId") UUID taskId);

    @Query("INSERT INTO Task.parentTask (taskId, parentId) VALUES (:taskId, :parentId)")
    @Transactional
    void addParentTask(@Param("taskId") UUID taskId, @Param("parentId") UUID parentId);

    @Query("SELECT t FROM Task t" +
            "LEFT JOIN FETCH t.parentTasks parentTask " +
            "WHERE t.isDeleted = false")
    @Transactional(readOnly = true)
    Iterable<Task> getParentTasks(@Param("taskId") UUID taskId);

    @Query("INSERT INTO Task.childTask (taskId, childId) VALUES (:taskId, :childId)")
    @Transactional
    void addChildTask(@Param("taskId") UUID taskId, @Param("childId") UUID childId);

    @Query("SELECT t FROM Task t" +
            "LEFT JOIN FETCH t.childTasks childTasks " +
            "WHERE t.isDeleted = false")
    @Transactional
    Iterable<Task> getChildTasks(@Param("taskId") UUID taskId);


    @Modifying
    @Query("INSERT INTO Task.tags (taskId, tagId) SELECT :taskId, t.uuid " +
            "FROM Tag t WHERE t.uuid IN :tagIds AND NOT EXISTS ( " +
            "SELECT 1 FROM Task.tags t2 WHERE t2.taskId = :taskId AND t2.tagId = t.uuid")
    @Transactional
    List<Task> addTagsToTask(@Param("taskId") UUID taskId, @Param("tagIds") Set<UUID> tagIds);

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
