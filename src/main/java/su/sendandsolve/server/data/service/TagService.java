package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.sendandsolve.server.data.datatransferobject.TagMapper;
import su.sendandsolve.server.data.datatransferobject.TagResponse;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.repository.TagRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class TagService implements IService<TagResponse, Tag, UUID> {
    private final TagRepository repository;
    private final TagMapper mapper;

    public TagService(TagRepository repository, TagMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    private Tag fromResponse(TagResponse response) {
        Tag tag = new Tag();
        tag.setUuid(response.uuid());
        tag.setName(response.name());
        return tag;
    }

    @Transactional
    @Override
    public TagResponse create(TagResponse entity) {
        Tag tag = fromResponse(entity);
        tag.setUuid(UUID.randomUUID());
        return mapper.toTagResponse(tag);
    }

    @Transactional
    @Override
    public Collection<TagResponse> saveAll(Collection<TagResponse> entities) {
        List<Tag> tags = repository.saveAll(entities.stream().map(this::fromResponse).toList());
        for (Tag tag : tags) tag.setUuid(UUID.randomUUID());
        return mapper.toTagResonseList(tags);
    }

    @Transactional
    @Override
    public TagResponse update(TagResponse entity) {
        return mapper.toTagResponse(repository.save(fromResponse(entity)));
    }

    @Override
    public TagResponse getById(UUID uuid) {
        return mapper.toTagResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Collection<TagResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toTagResponse).getContent();
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
