package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.domain.Resource;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.domain.Task;
import su.sendandsolve.server.data.repository.ResourceRepository;
import su.sendandsolve.server.data.repository.TagRepository;
import su.sendandsolve.server.data.repository.TaskRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/tasks") // Базовый путь для всех методов контроллера
public class TaskController extends SimpleBaseController<Task, UUID> {
    private final TagRepository tagRepository;
    private final ResourceRepository resourceRepository;

    public TaskController(TaskRepository repository, TagRepository tagRepository, ResourceRepository resourceRepository) {
        super(repository);
        this.tagRepository = tagRepository;
        this.resourceRepository = resourceRepository;
    }

    @PostMapping("/{taskId}/tags/{tagId}")
    @ResponseStatus(HttpStatus.OK) // или HttpStatus.NO_CONTENT если не нужно возвращать данные
    public ResponseEntity<Void> addTagToTask(
            @PathVariable UUID taskId,
            @PathVariable UUID tagId
    ) {
        Task task = repository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.addTag(tagRepository.findById(tagId).orElseThrow(() -> new EntityNotFoundException("Task not found")));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{taskId}/tags")
    public Iterable<Tag> getTaskTags(@PathVariable UUID taskId) {
        return repository.getReferenceById(taskId).getTags();
    }

    @GetMapping("/{taskId}/resources")
    public Iterable<Resource> getTaskResources(@PathVariable UUID taskId) {
        return repository.getReferenceById(taskId).getResources();
    }

    @PostMapping("/{taskId}/parenttask/{parentId}")
    @ResponseStatus(HttpStatus.OK) // или HttpStatus.NO_CONTENT если не нужно возвращать данные
    public ResponseEntity<Void> addParentTaskToTask(
            @PathVariable UUID taskId,
            @PathVariable UUID parentId
    ) {
        Task task = repository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.addParentTask(repository.findById(parentId).orElseThrow(() -> new EntityNotFoundException("Task not found")));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{taskId}/parenttasks")
    public Iterable<Task> getParentTasks(@PathVariable UUID taskId) {
        return repository.getReferenceById(taskId).getParentTasks();
    }

    @PostMapping("/{taskId}/childtask/{childId}")
    public ResponseEntity<Void> addChildTaskToTask(
            @PathVariable UUID taskId,
            @PathVariable UUID childId
    ) {
        Task task = repository.getReferenceById(taskId);
        task.addChildTask(repository.findById(childId).orElseThrow(() -> new EntityNotFoundException("Task not found")));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{taskId}/childtasks")
    public Iterable<Task> getChildTasks(@PathVariable UUID taskId) {
        return repository.getReferenceById(taskId).getChildTasks();
    }

    @PatchMapping("/{taskId}/tags")
    public ResponseEntity<List<Task>> addTagsToTask(
            @PathVariable UUID taskId,
            @RequestBody Set<UUID> tagIds
    ) {
        if (tagIds == null || tagIds.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Task task = repository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        Set<Tag> tags = tagIds
                .stream()
                .map(tagRepository::getReferenceById).collect(Collectors.toSet());

        task.getTags().addAll(tags);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{taskId}/resources")
    public ResponseEntity<List<Task>> addResourcesToTask(
            @PathVariable UUID taskId,
            @RequestBody Set<UUID> resourcesIds
    ) {
        if (resourcesIds == null || resourcesIds.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Task task = repository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        Set<Resource> resources = resourcesIds
                .stream()
                .map(resourceRepository::getReferenceById).collect(Collectors.toSet());

        task.getResources().addAll(resources);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}