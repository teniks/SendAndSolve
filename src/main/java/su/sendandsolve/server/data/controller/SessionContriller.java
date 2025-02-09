package su.sendandsolve.server.data.controller;

import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.datatransferobject.SessionResponse;
import su.sendandsolve.server.data.domain.Session;
import su.sendandsolve.server.data.repository.SessionRepository;
import su.sendandsolve.server.data.service.IService;
import su.sendandsolve.server.data.service.SessionService;

import java.util.UUID;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/sessions") // Базовый путь для всех методов контроллера
public class SessionContriller extends BaseController<SessionResponse, Session, UUID> {


    public SessionContriller(SessionService service) {
        super(service);
    }
}