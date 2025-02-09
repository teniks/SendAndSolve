package su.sendandsolve.server.data.datatransferobject;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import su.sendandsolve.server.data.domain.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    default UserResponse toUserResponse(User user) {    //map User to UserResponse
        return new UserResponse(user.getUuid(), user.getLogin(), user.getNickname(), null);
    }
    List<UserResponse> toUserResponseList(List<User> users); //map list of User to list of UserResponse
}
