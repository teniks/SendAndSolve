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
import su.sendandsolve.server.data.repository.UserRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NoteService implements IService<NoteResponse, Note, UUID>{
    private final NoteRepository repository;
    private final NoteMapper mapper;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository repository, TagRepository tagRepository, NoteMapper mapper, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    private Note fromResponse(NoteResponse response){
        Note note = new Note();
        note.setUuid(response.uuid());
        note.setTitle(response.title());
        note.setDescription(response.content());
        note.setDateCreation(response.creationDate());
        note.setUser(userRepository.findById(response.userId()).orElseThrow(EntityNotFoundException::new));
        if(response.tags() != null) note.setTags(new HashSet<>(tagRepository.findAllById(response.tags())));
        return note;
    }

    @Override
    public NoteResponse create(NoteResponse entity) {
        Note note = fromResponse(entity);
        note.setUuid(UUID.randomUUID());
        return mapper.toNoteResponse(repository.save(note));
    }

    @Override
    public Collection<NoteResponse> saveAll(Collection<NoteResponse> entities) {
        List<Note> notes = entities.stream().map(this::fromResponse).toList();
        for (Note note : notes) note.setUuid(UUID.randomUUID());
        return mapper.toNoteResponseList(repository.saveAll(notes));
    }

    @Override
    public NoteResponse update(NoteResponse entity) {
        return mapper.toNoteResponse(repository.save(fromResponse(entity)));
    }

    @Override
    public NoteResponse getById(UUID uuid) {
        return mapper.toNoteResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Collection<NoteResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toNoteResponse).getContent();
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

    public void deleteTagFromNote(UUID noteId, UUID tagId) {
        repository.findById(noteId).orElseThrow(EntityNotFoundException::new).getTags().remove(tagRepository.findById(tagId).orElseThrow(EntityNotFoundException::new));
    }
}
