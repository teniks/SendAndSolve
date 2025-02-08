package su.sendandsolve.server.data.datatransferobject;

import org.springframework.stereotype.Component;
import su.sendandsolve.server.data.domain.User;
import su.sendandsolve.server.data.util.MappingUtil;

@Component
public class UserMapper implements GenericMapper<UserDto, User> {

    @Override
    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        MappingUtil.copyFields(entity, dto);
        return dto;
    }

    @Override
    public User toEntity(UserDto dto) {
        User user = new User();
        MappingUtil.copyFields(dto, user);
        return user;
    }
}
