package su.sendandsolve.server.data.datatransferobject;

import org.mapstruct.Mapper;
import su.sendandsolve.server.data.domain.Team;

import java.util.Collection;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TeamMapper{

    default TeamResponse toTeamResponse(Team team){
        return new TeamResponse(team.getUuid(), team.getTitle(), team.getCreator().getUuid());
    }
    List<TeamResponse> toTeamResponseList(Collection<Team> teams);
}

