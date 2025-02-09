package su.sendandsolve.server.data.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.datatransferobject.TeamResponse;
import su.sendandsolve.server.data.domain.*;
import su.sendandsolve.server.data.service.TeamService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/teams") // Базовый путь для всех методов контроллера
public class TeamController extends BaseController<TeamResponse, Team, UUID> {

    public TeamController(TeamService service) {
        super(service);
    }


    @PostMapping("/{teamId}/users/{userId}")
    public ResponseEntity<Void> addMemberToTeam(
            @PathVariable UUID teamId,
            @PathVariable UUID userId
    ) {
        ((TeamService)service).addMemberToTeam(teamId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{teamId}/users")
    public Iterable<User> getTeamMembers(@PathVariable UUID teamId) {
        return ((TeamService)service).getTeamMembers(teamId);
    }

    @PatchMapping("/{teamId}/users")
    public ResponseEntity<List<Task>> addTeamMembers(
            @PathVariable UUID teamId,
            @RequestBody Set<UUID> ids
    ) {

        return ResponseEntity.status(HttpStatus.CREATED).body(((TeamService)service).addTeamMembers(teamId, (List<UUID>) ids));
    }
}
