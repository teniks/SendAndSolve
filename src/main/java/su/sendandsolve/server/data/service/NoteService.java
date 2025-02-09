package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.sendandsolve.server.data.datatransferobject.NoteMapper;
import su.sendandsolve.server.data.datatransferobject.NoteResponse;
import su.sendandsolve.server.data.domain.Note;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.repository.NoteRepository;
import su.sendandsolve.server.data.repository.TagRepository;

import java.util.Collection;
import java.util.UUID;

@Service
public class NoteService implements IService<NoteResponse, Note, UUID>{
    private final NoteRepository repository;
    private final NoteMapper mapper;
    private final TagRepository tagRepository;

    public NoteService(NoteRepository repository, TagRepository tagRepository, NoteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.tagRepository = tagRepository;
    }

    @Override
    public NoteResponse create(Note entity) {
        return mapper.toNoteResponse(repository.save(entity));
    }

    @Override
    public Collection<NoteResponse> saveAll(Collection<Note> entities) {
        return mapper.toNoteResponseList(repository.saveAll(entities));
    }

    @Override
    public NoteResponse update(Note entity) {
        return mapper.toNoteResponse(repository.save(entity));
    }

    @Override
    public NoteResponse getById(UUID uuid) {
        return mapper.toNoteResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<NoteResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toNoteResponse);
    }

    @Override
    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public void deleteAllByIdInBatch(Collection<UUID> uuids) {
        repository.deleteAllByIdInBatch(uuids);
    }

    public void addTagToNote(UUID noteId, UUID tagId) {
        repository.findById(noteId).orElseThrow(EntityNotFoundException::new).addTag(tagRepository.findById(tagId).orElseThrow(EntityNotFoundException::new));
    }

    public Iterable<Tag> getNoteTags(UUID noteId) {
        return repository.findById(noteId).orElseThrow(EntityNotFoundException::new).getTags();
    }
}
