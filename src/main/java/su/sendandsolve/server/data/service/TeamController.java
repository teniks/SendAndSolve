package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.domain.*;
import su.sendandsolve.server.data.repository.TeamRepository;
import su.sendandsolve.server.data.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/teams") // Базовый путь для всех методов контроллера
public class TeamController extends SimpleBaseController<Team, UUID> {
    UserRepository userRepository;

    public TeamController(TeamRepository repository, UserRepository userRepository) {
        super(repository);
        this.userRepository = userRepository;
    }

    @PostMapping("/{teamId}/users/{userId}")
    public ResponseEntity<Void> addMemberToTeam(
            @PathVariable UUID teamId,
            @PathVariable UUID userId
    ) {
        Team team = repository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team not found"));
        team.setMember(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found")));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{teamId}/users")
    public Iterable<User> getTeamMembers(@PathVariable UUID teamId) {
        return repository.getReferenceById(teamId).getMembers();
    }

    @PatchMapping("/{teamId}/users")
    public ResponseEntity<List<Task>> addResourcesToTask(
            @PathVariable UUID teamId,
            @RequestBody Set<UUID> usersds
    ) {
        if (usersds == null || usersds.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Team team = repository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team not found"));

        Set<User> resources = usersds
                .stream()
                .map(userRepository::getReferenceById).collect(Collectors.toSet());

        team.getMembers().addAll(resources);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
