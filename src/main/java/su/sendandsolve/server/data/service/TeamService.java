package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.sendandsolve.server.data.datatransferobject.TeamMapper;
import su.sendandsolve.server.data.datatransferobject.TeamResponse;
import su.sendandsolve.server.data.datatransferobject.UserMapper;
import su.sendandsolve.server.data.datatransferobject.UserResponse;
import su.sendandsolve.server.data.domain.Team;
import su.sendandsolve.server.data.repository.TeamRepository;
import su.sendandsolve.server.data.repository.UserRepository;

import java.util.*;

@Service
public class TeamService implements IService<TeamResponse, Team, UUID> {
    private final TeamRepository repository;
    private final UserRepository userRepository;
    private final TeamMapper mapper;
    private final UserMapper userMapper;

    public TeamService(TeamRepository repository, TeamMapper mapper, UserRepository userRepository, UserMapper userMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Team fromResponse(TeamResponse response){
        Team team = new Team();
        team.setUuid(response.uuid());
        team.setTitle(response.title());
        if(response.members() != null) team.setMembers(new HashSet<>(userRepository.findAllById(response.members())));
        team.setCreator(userRepository.findById(response.creatorId()).orElseThrow(EntityNotFoundException::new));
        return team;
    }

    @Override
    public TeamResponse create(TeamResponse entity) {
        Team team = fromResponse(entity);
        team.setUuid(UUID.randomUUID());
        return mapper.toTeamResponse(repository.save(team));
    }

    @Override
    public Collection<TeamResponse> saveAll(Collection<TeamResponse> entities) {
        List<Team> teams = entities.stream().map(this::fromResponse).toList();
        for(Team team : teams) team.setUuid(UUID.randomUUID());
        return repository.saveAll(teams).stream().map(mapper::toTeamResponse).toList();
    }

    @Override
    public TeamResponse update(TeamResponse entity) {
        return mapper.toTeamResponse(repository.save(fromResponse(entity)));
    }

    @Override
    public TeamResponse getById(UUID uuid) {
        return mapper.toTeamResponse(repository.findById(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Collection<TeamResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toTeamResponse).getContent();
    }

    @Override
    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public void deleteAllByIdInBatch(Collection<UUID> uuids) {
        repository.deleteAllByIdInBatch(uuids);
    }

    public void addMemberToTeam(UUID teamId, UUID userId) {
        repository.findById(teamId).orElseThrow(EntityNotFoundException::new).getMembers().add(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
    }

    public List<UserResponse> getTeamMembers(UUID teamId, Pageable pageable) {
        return userMapper.toUserResponseList(new ArrayList<>((repository.findById(teamId).orElseThrow(EntityNotFoundException::new).getMembers())));
    }

    public void addTeamMembers(UUID teamId, Set<UUID> userIds) {
        repository.findById(teamId).orElseThrow(EntityNotFoundException::new).getMembers().addAll(userRepository.findAllById(userIds));
    }

    public void deleteMemberFromTeam(UUID teamId, UUID userId) {
        repository.findById(teamId).orElseThrow(EntityNotFoundException::new).getMembers().remove(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
    }
}
