package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.sendandsolve.server.data.datatransferobject.TagMapper;
import su.sendandsolve.server.data.datatransferobject.TagResponse;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.repository.TagRepository;

import java.util.Collection;
import java.util.UUID;

@Service
public class TagService implements IService<TagResponse, Tag, UUID> {
    private final TagRepository repository;
    private final TagMapper mapper;

    public TagService(TagRepository repository, TagMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public TagResponse create(Tag entity) {
        return mapper.toTagResponse(repository.save(entity));
    }

    @Transactional
    @Override
    public Collection<TagResponse> saveAll(Collection<Tag> entities) {
        return mapper.toTagResonseList(repository.saveAll(entities));
    }

    @Transactional
    @Override
    public TagResponse update(Tag entity) {
        return mapper.toTagResponse(repository.save(entity));
    }

    @Override
    public TagResponse getById(UUID uuid) {
        return mapper.toTagResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<TagResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toTagResponse);
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
}
