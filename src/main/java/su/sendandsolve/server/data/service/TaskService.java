package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.sendandsolve.server.data.datatransferobject.TaskMapper;
import su.sendandsolve.server.data.datatransferobject.TaskResponse;
import su.sendandsolve.server.data.domain.Resource;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.domain.Task;
import su.sendandsolve.server.data.repository.TaskRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TaskService implements IService<TaskResponse, Task, UUID> {
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repository, TaskMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional
    @Override
    public TaskResponse create(Task entity) {
        return mapper.toTaskResponse(repository.save(entity));
    }

    @Transactional
    @Override
    public Collection<TaskResponse> saveAll(Collection<Task> entities){
        return mapper.toTaskResponseList(repository.saveAll(entities));
    }

    @Transactional
    @Override
    public TaskResponse update(Task entity) {
        return mapper.toTaskResponse(repository.save(entity));
    }

    @Override
    public TaskResponse getById(UUID uuid) {
        return mapper.toTaskResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<TaskResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toTaskResponse);
    }

    @Transactional
    @Override
    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Transactional
    @Override
    public void deleteAllByIdInBatch(Collection<UUID> uuids) {
        repository.deleteAllByIdInBatch(uuids);
    }

    public Iterable<Tag> getTags(UUID taskId) {
        return repository.getTags(taskId);
    }

    public Iterable<Resource> getResources(UUID taskId) {
        return repository.getResources(taskId);
    }

    public void addParentTaskToTask(UUID taskId, UUID parentId) {
        repository.addParentTask(taskId, parentId);
    }

    public Iterable<Task> getParentTasks(UUID taskId) {
        return repository.getParentTasks(taskId);
    }

    public void addChildTaskToTask(UUID taskId, UUID childId) {
        repository.addChildTask(taskId, childId);
    }

    public Iterable<Task> getChildTasks(UUID taskId) {
        return repository.getChildTasks(taskId);
    }

    public List<Task> addTagsToTask(UUID taskId, Set<UUID> tagIds) {
        return repository.addTagsToTask(taskId, tagIds);
    }

    public List<Task> addResourcesToTask(UUID taskId, Set<UUID> resourcesIds) {
        return repository.addResourcesToTask(taskId, resourcesIds);
    }
}
