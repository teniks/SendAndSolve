package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.sendandsolve.server.data.datatransferobject.SessionMapper;
import su.sendandsolve.server.data.datatransferobject.SessionResponse;
import su.sendandsolve.server.data.domain.Session;
import su.sendandsolve.server.data.repository.SessionRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService implements IService<SessionResponse, Session, UUID>{
    private final SessionRepository repository;
    private final SessionMapper mapper;

    public SessionService(SessionRepository repository, SessionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public SessionResponse create(Session entity) {
        return mapper.toSessionResponse(repository.save(entity));
    }

    @Override
    public Collection<SessionResponse> saveAll(Collection<Session> entities) {
        return List.of();
    }

    @Override
    public SessionResponse update(Session entity) {
        return mapper.toSessionResponse(repository.save(entity));
    }

    @Override
    public SessionResponse getById(UUID uuid) {
        return mapper.toSessionResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<SessionResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toSessionResponse);
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
