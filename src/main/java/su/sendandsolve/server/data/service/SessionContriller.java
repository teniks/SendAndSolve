package su.sendandsolve.server.data.service;

import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.domain.Session;
import su.sendandsolve.server.data.repository.SessionRepository;

import java.util.UUID;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/sessions") // Базовый путь для всех методов контроллера
public class SessionContriller extends SimpleBaseController<Session, UUID> {

    public SessionContriller(SessionRepository repository) {
        super(repository);
    }
}