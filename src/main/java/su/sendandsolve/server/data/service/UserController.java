package su.sendandsolve.server.data.service;

import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.datatransferobject.UserDto;
import su.sendandsolve.server.data.datatransferobject.UserMapper;
import su.sendandsolve.server.data.domain.User;
import su.sendandsolve.server.data.repository.UserRepository;

import java.util.UUID;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/users") // Базовый путь для всех методов контроллера
public class UserController extends BaseController<UserDto, User, UUID> {

    public UserController(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    @GetMapping("/{login}")
    public UserDto getUserByLogin(@PathVariable String login) {
        User user = ((UserRepository)repository).findByLogin(login);
        return mapper.toDto(user);
    }
}

