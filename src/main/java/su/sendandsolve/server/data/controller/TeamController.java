package su.sendandsolve.server.data.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.datatransferobject.TeamResponse;
import su.sendandsolve.server.data.datatransferobject.UserResponse;
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
    public List<UserResponse> getTeamMembers(@PathVariable UUID teamId, @PageableDefault Pageable pageable) {
        return ((TeamService)service).getTeamMembers(teamId, pageable);
    }

    @PatchMapping("/{teamId}/users")
    public ResponseEntity<Void> addTeamMembers(
            @PathVariable UUID teamId,
            @RequestBody Set<UUID> ids
    ) {
        ((TeamService)service).addTeamMembers(teamId, ids);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{teamId}/users/{userId}")
    public ResponseEntity<Void> deleteMemberFromTeam(@PathVariable UUID teamId, @PathVariable UUID userId) {
        try {
            ((TeamService)service).deleteMemberFromTeam(teamId, userId);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
