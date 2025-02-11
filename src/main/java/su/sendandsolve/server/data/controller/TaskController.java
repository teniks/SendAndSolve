package su.sendandsolve.server.data.controller;

import jakarta.persistence.EntityNotFoundException;
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

    @PutMapping("/{taskId}/tags/{tagId}")
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
    public ResponseEntity<Iterable<Tag>> getTaskTags(@PathVariable UUID taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(((TaskService)service).getTags(taskId));
    }

    @GetMapping("/{taskId}/resources")
    public ResponseEntity<Iterable<Resource>> getTaskResources(@PathVariable UUID taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(((TaskService)service).getResources(taskId));
    }

    @PostMapping("/{taskId}/parenttasks/{parentId}")
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

    @PostMapping("/{taskId}/tags")
    @Transactional
    public ResponseEntity<List<Task>> addTagsToTask(
            @PathVariable UUID taskId,
            @RequestBody Set<UUID> tagIds
    ) {
        if (tagIds == null || tagIds.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        ((TaskService)service).addTagsToTask(taskId, tagIds);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{taskId}/resources")
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

    @DeleteMapping("/{taskId}/tags/{tagId}")
    @Transactional
    public ResponseEntity<Void> deleteTagFromTask(@PathVariable UUID taskId, @PathVariable UUID tagId) {
        try {
            ((TaskService)service).deleteTagFromTask(taskId, tagId);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{taskId}/resources/{resourceId}")
    @Transactional
    public ResponseEntity<Void> deleteResourceFromTask(@PathVariable UUID taskId, @PathVariable UUID resourceId) {
        try {
            ((TaskService)service).deleteResourceFromTask(taskId, resourceId);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{taskId}/childtasks/{childId}")
    @Transactional
    public ResponseEntity<Void> deleteChildTaskFromTask(@PathVariable UUID taskId, @PathVariable UUID childId) {
        try {
            ((TaskService)service).deleteChildTaskFromTask(taskId, childId);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{taskId}/parenttasks/{childId}")
    @Transactional
    public ResponseEntity<Void> deleteParentTaskFromTask(@PathVariable UUID taskId, @PathVariable UUID parentId) {
        try {
            ((TaskService)service).deleteParentTaskFromTask(taskId, parentId);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}