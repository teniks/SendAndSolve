package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.NotSupportedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.sendandsolve.server.data.datatransferobject.UserMapper;
import su.sendandsolve.server.data.datatransferobject.UserResponse;
import su.sendandsolve.server.data.domain.User;
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

    public UserResponse findByLogin(String login) {
        User user = ((UserRepository)repository).findByLogin(login);
        return mapper.toUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse create(User entity) {
        return mapper.toUserResponse(repository.save(entity));
    }

    @Transactional
    @Override
    public Collection<UserResponse> saveAll(Collection<User> entities) {
        return List.of();
    }

    @Transactional
    @Override
    public UserResponse update(User entity) {
        return mapper.toUserResponse(repository.save(entity));
    }

    @Override
    public UserResponse getById(UUID uuid) {
        return mapper.toUserResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<UserResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toUserResponse);
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
