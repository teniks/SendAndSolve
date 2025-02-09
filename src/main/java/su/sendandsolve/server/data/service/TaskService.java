package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.sendandsolve.server.data.datatransferobject.TaskMapper;
import su.sendandsolve.server.data.datatransferobject.TaskResponse;
import su.sendandsolve.server.data.domain.Resource;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.domain.Task;
import su.sendandsolve.server.data.repository.ResourceRepository;
import su.sendandsolve.server.data.repository.TagRepository;
import su.sendandsolve.server.data.repository.TaskRepository;
import su.sendandsolve.server.data.repository.UserRepository;

import java.util.*;

@Service
public class TaskService implements IService<TaskResponse, Task, UUID> {
    private final TaskRepository repository;
    private final TagRepository tagRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repository, TaskMapper mapper, TagRepository tagRepository, ResourceRepository resourceRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.tagRepository = tagRepository;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    private Task fromResponseToTask(TaskResponse entity){
        Task task = new Task();
        task.setUuid(entity.uuid());
        task.setTitle(entity.title());
        task.setDescription(entity.description());
        task.setStatus(entity.status());
        task.setStartDate(entity.startDate());
        task.setEndDate(entity.endDate());
        task.setProgress(entity.progress());
        task.setPriority(entity.priority());
        task.setDateCreation(entity.dateCreation());
        if(entity.tags() != null) task.setTags(tagRepository.findAllByIds(entity.tags()));
        if(entity.resources() != null) task.setResources(new HashSet<>(resourceRepository.findAllById(entity.resources())));
        if(entity.childTasks() != null) task.setChildTasks(new HashSet<>(repository.findAllById(entity.childTasks())));
        if(entity.parentTasks() != null) task.setParentTasks(new HashSet<>(repository.findAllById(entity.parentTasks())));
        task.setCreator(userRepository.findById(entity.creatorId()).orElseThrow(EntityNotFoundException::new));
        return task;
    }

    @Override
    public TaskResponse create(TaskResponse entity) {
        Task task = fromResponseToTask(entity);
        task.setUuid(UUID.randomUUID());
        return mapper.toTaskResponse(repository.save(task));
    }

    @Override
    @Transactional
    public Collection<TaskResponse> saveAll(Collection<TaskResponse> entities){
        List<Task> tasks = repository.saveAll(entities.stream().map(this::fromResponseToTask).toList());
        for (Task task : tasks) task.setUuid(UUID.randomUUID());
        return mapper.toTaskResponseList(tasks);
    }

    @Override
    @Transactional
    public TaskResponse update(TaskResponse entity) {
        return mapper.toTaskResponse(repository.save(fromResponseToTask(entity)));
    }

    @Override
    @Transactional
    public TaskResponse getById(UUID uuid) {
        return mapper.toTaskResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    @Transactional
    public Collection<TaskResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toTaskResponse).getContent();
    }

    @Override
    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public void deleteAllByIdInBatch(Collection<UUID> uuids) {
        repository.deleteAllByIdInBatch(uuids);
    }

    @Transactional(readOnly = true)
    public Iterable<Tag> getTags(UUID taskId) {
        return repository.findById(taskId).orElseThrow(EntityNotFoundException::new).getTags().stream().toList();
    }

    @Transactional(readOnly = true)
    public Iterable<Resource> getResources(UUID taskId) {
        return repository.findById(taskId).orElseThrow(EntityNotFoundException::new).getResources().stream().toList();
    }

    @Transactional
    public void addParentTaskToTask(UUID taskId, UUID parentId) {
        repository.findById(taskId).orElseThrow(EntityNotFoundException::new).getParentTasks().add(repository.findById(parentId).orElseThrow(EntityNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public Iterable<Task> getParentTasks(UUID taskId) {
        return repository.findById(taskId).orElseThrow(EntityNotFoundException::new).getParentTasks();
    }

    @Transactional
    public void addChildTaskToTask(UUID taskId, UUID childId) {
        repository.findById(taskId).orElseThrow(EntityNotFoundException::new).getChildTasks().add(repository.findById(childId).orElseThrow(EntityNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public Iterable<Task> getChildTasks(UUID taskId) {
        return repository.findById(taskId).orElseThrow(EntityNotFoundException::new).getChildTasks();
    }

    @Transactional
    public void addTagsToTask(UUID taskId, Set<UUID> tagIds) {
        repository.findById(taskId).orElseThrow(EntityNotFoundException::new).getTags().addAll((Collection<Tag>)tagRepository.findAllByIds(tagIds));
    }

    @Transactional
    public List<Task> addResourcesToTask(UUID taskId, Set<UUID> resourcesIds) {
        return repository.addResourcesToTask(taskId, resourcesIds);
    }

    @Transactional
    public void deleteTagFromTask(UUID taskId, UUID tagId) {
        repository.findById(taskId).orElseThrow(EntityNotFoundException::new).getTags().removeIf(tag -> tag.getUuid().equals(tagId));
    }

    @Transactional
    public void deleteResourceFromTask(UUID taskId, UUID resourceId) {
        repository.findById(taskId).orElseThrow(EntityNotFoundException::new).getResources().removeIf(resource -> resource.getUuid().equals(resourceId));
    }

    @Transactional
    public void deleteChildTaskFromTask(UUID taskId, UUID childId) {
        repository.findById(taskId).orElseThrow(EntityNotFoundException::new).getChildTasks().removeIf(child -> child.getUuid().equals(childId));
    }

    @Transactional
    public void deleteParentTaskFromTask(UUID taskId, UUID parentId) {
        repository.findById(taskId).orElseThrow(EntityNotFoundException::new).getParentTasks().removeIf(parent -> parent.getUuid().equals(parentId));
    }
}
