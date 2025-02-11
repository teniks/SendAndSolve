package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.sendandsolve.server.data.datatransferobject.UserMapper;
import su.sendandsolve.server.data.datatransferobject.UserResponse;
import su.sendandsolve.server.data.domain.User;
import su.sendandsolve.server.data.exception.AlreadyExistsException;
import su.sendandsolve.server.data.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IService<UserResponse, User, UUID> {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    private User fromResponse(UserResponse entity) {
        User user = new User();
        user.setUuid(entity.uuid());
        user.setNickname(entity.nickname());
        user.setLogin(entity.login());
        user.setPassword(entity.password());
        return user;
    }

    public UserResponse findByLogin(String login) {
        User user = ((UserRepository)repository).findByLogin(login);
        return mapper.toUserResponse(user);
    }

    @Override
    public UserResponse create(UserResponse entity) throws AlreadyExistsException {
        User user = fromResponse(entity);
        user.setUuid(UUID.randomUUID());
        if(repository.findByLogin(user.getLogin()) != null) throw new AlreadyExistsException("This user already exists");
        return mapper.toUserResponse(repository.save(user));
    }

    @Override
    public Collection<UserResponse> saveAll(Collection<UserResponse> entities) {
        return List.of();
    }

    @Override
    public UserResponse update(UserResponse entity) throws EntityNotFoundException {
        return mapper.toUserResponse(repository.save(fromResponse(entity)));
    }

    @Override
    public UserResponse getById(UUID uuid) {
        return mapper.toUserResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Collection<UserResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toUserResponse).getContent();
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
