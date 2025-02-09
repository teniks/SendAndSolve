package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.sendandsolve.server.data.datatransferobject.TeamMapper;
import su.sendandsolve.server.data.datatransferobject.TeamResponse;
import su.sendandsolve.server.data.domain.Task;
import su.sendandsolve.server.data.domain.Team;
import su.sendandsolve.server.data.domain.User;
import su.sendandsolve.server.data.repository.TeamRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class TeamService implements IService<TeamResponse, Team, UUID> {
    private final TeamRepository repository;
    private final TeamMapper mapper;

    public TeamService(TeamRepository repository, TeamMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public TeamResponse create(Team entity) {
        return mapper.toTeamResponse(repository.save(entity));
    }

    @Transactional
    @Override
    public Collection<TeamResponse> saveAll(Collection<Team> entities) {
        return repository.saveAll(entities).stream().map(mapper::toTeamResponse).toList();
    }

    @Transactional
    @Override
    public TeamResponse update(Team entity) {
        return mapper.toTeamResponse(repository.save(entity));
    }

    @Override
    public TeamResponse getById(UUID uuid) {
        return mapper.toTeamResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<TeamResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toTeamResponse);
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

    @Transactional
    public void addMemberToTeam(UUID teamId, UUID userId) {
        repository.addMemberToTeam(teamId, userId);
    }

    @Transactional
    public Iterable<User> getTeamMembers(UUID teamId) {
        return repository.getTeamMembers(teamId);
    }

    public List<Task> addTeamMembers(UUID teamId, List<UUID> userIds) {
        return repository.addTeamMembers(teamId, userIds);
    }
}
