package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.sendandsolve.server.data.datatransferobject.ResourceMapper;
import su.sendandsolve.server.data.datatransferobject.ResourceResponse;
import su.sendandsolve.server.data.domain.Resource;
import su.sendandsolve.server.data.repository.ResourceRepository;
import su.sendandsolve.server.data.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResourceService implements IService<ResourceResponse, Resource, UUID>{
    private final ResourceRepository repository;
    private final UserRepository userRepository;
    private final ResourceMapper mapper;

    public ResourceService(ResourceRepository repository, ResourceMapper mapper, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    private Resource fromResponse(ResourceResponse response){
        Resource resource = new Resource();
        resource.setUuid(response.uuid());
        resource.setCreator(userRepository.findById(response.creatorId()).orElseThrow(EntityNotFoundException::new));
        resource.setHash(response.hash());
        resource.setFileLocation(response.filePath());
        resource.setByteSize(response.size());
        resource.setUploadTimestamp(response.uploadTime());
        resource.setMetadata(response.metadata());
        return resource;
    }

    @Override
    public ResourceResponse create(ResourceResponse entity) {
        Resource resource = fromResponse(entity);
        resource.setUuid(UUID.randomUUID());
        return mapper.toResourceResponse(repository.save(resource));
    }

    @Override
    public Collection<ResourceResponse> saveAll(Collection<ResourceResponse> entities) {
        List<Resource> resources = entities.stream().map(this::fromResponse).toList();
        for (Resource resource : resources) resource.setUuid(UUID.randomUUID());
        return mapper.toResourceResponseList(repository.saveAll(resources));
    }

    @Override
    public ResourceResponse update(ResourceResponse entity) {
        return mapper.toResourceResponse(repository.save(fromResponse(entity)));
    }

    @Override
    public ResourceResponse getById(UUID uuid) {
        return mapper.toResourceResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Collection<ResourceResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResourceResponse).getContent();
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
