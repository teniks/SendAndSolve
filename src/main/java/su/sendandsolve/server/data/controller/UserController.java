package su.sendandsolve.server.data.controller;

import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.datatransferobject.UserResponse;
import su.sendandsolve.server.data.domain.User;
import su.sendandsolve.server.data.service.UserService;

import java.util.UUID;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/users") // Базовый путь для всех методов контроллера
public class UserController extends BaseController<UserResponse, User, UUID> {

    public UserController(UserService service) {
        super(service);
    }

    @GetMapping("/{login}")
    public UserResponse findByLogin(@PathVariable String login) {
        return ((UserService)service).findByLogin(login);
    }
}

