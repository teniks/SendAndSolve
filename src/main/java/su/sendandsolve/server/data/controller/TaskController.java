package su.sendandsolve.server.data.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.datatransferobject.TaskResponse;
import su.sendandsolve.server.data.domain.Resource;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.domain.Task;
import su.sendandsolve.server.data.service.TaskService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/tasks") // Базовый путь для всех методов контроллера
public class TaskController extends BaseController<TaskResponse, Task, UUID> {


    public TaskController(TaskService service) {
        super(service);
    }

    @PostMapping("/{taskId}/tags/{tagId}")
    @Transactional
    @ResponseStatus(HttpStatus.OK) // или HttpStatus.NO_CONTENT если не нужно возвращать данные
    public ResponseEntity<Void> addTagToTask(
            @PathVariable UUID taskId,
            @PathVariable UUID tagId
    ) {
        ((TaskService)service).addTagsToTask(taskId, Set.of(tagId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{taskId}/tags")
    public Iterable<Tag> getTaskTags(@PathVariable UUID taskId) {
        return ((TaskService)service).getTags(taskId);
    }

    @GetMapping("/{taskId}/resources")
    public Iterable<Resource> getTaskResources(@PathVariable UUID taskId) {
        return ((TaskService)service).getResources(taskId);
    }

    @PostMapping("/{taskId}/parenttask/{parentId}")
    @Transactional
    @ResponseStatus(HttpStatus.OK) // или HttpStatus.NO_CONTENT если не нужно возвращать данные
    public ResponseEntity<Void> addParentTaskToTask(
            @PathVariable UUID taskId,
            @PathVariable UUID parentId
    ) {
        ((TaskService)service).addParentTaskToTask(taskId, parentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{taskId}/parenttasks")
    public Iterable<Task> getParentTasks(@PathVariable UUID taskId) {
        return ((TaskService)service).getParentTasks(taskId);
    }

    @PostMapping("/{taskId}/childtask/{childId}")
    @Transactional
    public ResponseEntity<Void> addChildTaskToTask(
            @PathVariable UUID taskId,
            @PathVariable UUID childId
    ) {
        ((TaskService)service).addChildTaskToTask(taskId, childId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{taskId}/childtasks")
    @Transactional
    public Iterable<Task> getChildTasks(@PathVariable UUID taskId) {
        return ((TaskService)service).getChildTasks(taskId);
    }

    @PatchMapping("/{taskId}/tags")
    @Transactional
    public ResponseEntity<List<Task>> addTagsToTask(
            @PathVariable UUID taskId,
            @RequestBody Set<UUID> tagIds
    ) {
        if (tagIds == null || tagIds.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(((TaskService)service).addTagsToTask(taskId, tagIds));
    }

    @PatchMapping("/{taskId}/resources")
    @Transactional
    public ResponseEntity<List<Task>> addResourcesToTask(
            @PathVariable UUID taskId,
            @RequestBody Set<UUID> resourcesIds
    ) {
        if (resourcesIds == null || resourcesIds.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(((TaskService)service).addResourcesToTask(taskId, resourcesIds));
    }
}