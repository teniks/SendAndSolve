package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.sendandsolve.server.data.datatransferobject.ResourceMapper;
import su.sendandsolve.server.data.datatransferobject.ResourceResponse;
import su.sendandsolve.server.data.domain.Resource;
import su.sendandsolve.server.data.repository.ResourceRepository;

import java.util.Collection;
import java.util.UUID;

@Service
public class ResourceService implements IService<ResourceResponse, Resource, UUID>{
    private final ResourceRepository repository;
    private final ResourceMapper mapper;

    public ResourceService(ResourceRepository repository, ResourceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ResourceResponse create(Resource entity) {
        return mapper.toResourceResponse(repository.save(entity));
    }

    @Override
    public Collection<ResourceResponse> saveAll(Collection<Resource> entities) {
        return mapper.toResourceResponseList(repository.saveAll(entities));
    }

    @Override
    public ResourceResponse update(Resource entity) {
        return mapper.toResourceResponse(repository.save(entity));
    }

    @Override
    public ResourceResponse getById(UUID uuid) {
        return mapper.toResourceResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<ResourceResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResourceResponse);
    }

    @Override
    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public void deleteAllByIdInBatch(Collection<UUID> uuids) {
        repository.deleteAllByIdInBatch(uuids);
    }
}
