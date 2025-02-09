package su.sendandsolve.server.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import su.sendandsolve.server.data.domain.Task;
import su.sendandsolve.server.data.domain.Team;
import su.sendandsolve.server.data.domain.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    @Transactional
    @Query("INSERT INTO team_member (team_id, user_id) VALUES (:teamId, :userId)")
    void addMemberToTeam(@Param("teamId") UUID teamId, @Param("userId") UUID userId);

    @Query("SELECT t FROM TeamMembers WHERE team_id = :teamId")
    @Transactional(readOnly = true)
    Page<User> getTeamMembers(@Param("teamId") UUID teamId);

    @Query("INSERT INTO TeamMembers (id_team, id_user) SELECT :teamId, u.uuid FROM User u WHERE u.uuid IN :userIds")
    @Transactional
    List<Task> addTeamMembers(@Param("teamId") UUID teamId, @Param("userIds") List<UUID> userIds);
}
