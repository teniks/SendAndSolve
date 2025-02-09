package su.sendandsolve.server.data.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.datatransferobject.NoteResponse;
import su.sendandsolve.server.data.domain.Note;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.repository.NoteRepository;
import su.sendandsolve.server.data.repository.TagRepository;
import su.sendandsolve.server.data.service.IService;
import su.sendandsolve.server.data.service.NoteService;

import java.util.UUID;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/notes") // Базовый путь для всех методов контроллера
public class NoteController extends BaseController<NoteResponse, Note, UUID> {

    public NoteController(NoteService service) {
        super(service);
    }

    @PostMapping("/{noteId}/tags/{tagId}")
    @ResponseStatus(HttpStatus.OK) // или HttpStatus.NO_CONTENT если не нужно возвращать данные
    public ResponseEntity<Void> addTagToNote(
            @PathVariable UUID noteId,
            @PathVariable UUID tagId
    ) {
        ((NoteService)service).addTagToNote(noteId, tagId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{noteId}/tags")
    public Iterable<Tag> getNoteTags(@PathVariable UUID noteId) {
        return ((NoteService)service).getNoteTags(noteId);
    }
}