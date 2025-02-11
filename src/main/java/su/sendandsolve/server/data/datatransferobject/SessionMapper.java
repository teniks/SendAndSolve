package su.sendandsolve.server.data.datatransferobject;

import org.mapstruct.Mapper;
import su.sendandsolve.server.data.domain.Session;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    default SessionResponse toSessionResponse(Session session){
        return new SessionResponse(session.getUuid(), session.getUser().getUuid(), session.getUserToken(),
                session.getExpiryDate(), session.getDateActivity(), session.getDevice());
    }
    List<SessionResponse> toSessionResponseList(List<Session> sessions);
}
