package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.domain.Note;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.repository.NoteRepository;
import su.sendandsolve.server.data.repository.TagRepository;

import java.util.UUID;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/notes") // Базовый путь для всех методов контроллера
public class NoteController extends SimpleBaseController<Note, UUID> {
    private TagRepository tagRepository;

    public NoteController(NoteRepository repository, TagRepository tagRepository) {
        super(repository);
        this.tagRepository = tagRepository;
    }

    @PostMapping("/{noteId}/tags/{tagId}")
    @ResponseStatus(HttpStatus.OK) // или HttpStatus.NO_CONTENT если не нужно возвращать данные
    public ResponseEntity<Void> addTagToNote(
            @PathVariable UUID noteId,
            @PathVariable UUID tagId
    ) {
        Note note = repository.findById(noteId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        note.addTag(tagRepository.findById(tagId).orElseThrow(() -> new EntityNotFoundException("Task not found")));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{noteId}/tags")
    public Iterable<Tag> getNoteTags(@PathVariable UUID noteId) {
        return repository.getReferenceById(noteId).getTags();
    }
}