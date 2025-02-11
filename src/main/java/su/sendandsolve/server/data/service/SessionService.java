package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.sendandsolve.server.data.datatransferobject.SessionMapper;
import su.sendandsolve.server.data.datatransferobject.SessionResponse;
import su.sendandsolve.server.data.domain.Session;
import su.sendandsolve.server.data.repository.SessionRepository;
import su.sendandsolve.server.data.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService implements IService<SessionResponse, Session, UUID>{
    private final SessionRepository repository;
    private final UserRepository userRepository;
    private final SessionMapper mapper;

    public SessionService(SessionRepository repository, SessionMapper mapper, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    private Session fromResponse(SessionResponse response){
        Session session = new Session();
        session.setUuid(response.uuid());
        session.setUser(userRepository.findById(response.userId()).orElseThrow(EntityNotFoundException::new));
        session.setUserToken(response.token());
        session.setDateActivity(response.lastActivity());
        session.setExpiryDate(response.expiryDate());
        session.setDevice(response.deviceInfo());
        return session;
    }

    @Override
    public SessionResponse create(SessionResponse entity) {
        Session session = fromResponse(entity);
        session.setUuid(UUID.randomUUID());
        return mapper.toSessionResponse(repository.save(session));
    }

    @Override
    public Collection<SessionResponse> saveAll(Collection<SessionResponse> entities) {
        return List.of();
    }

    @Override
    public SessionResponse update(SessionResponse entity) {
        return mapper.toSessionResponse(repository.save(fromResponse(entity)));
    }

    @Override
    public SessionResponse getById(UUID uuid) {
        return mapper.toSessionResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Collection<SessionResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toSessionResponse).getContent();
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
