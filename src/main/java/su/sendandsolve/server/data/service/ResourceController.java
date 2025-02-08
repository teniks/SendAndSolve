package su.sendandsolve.server.data.service;

import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.domain.Resource;
import su.sendandsolve.server.data.repository.ResourceRepository;

import java.util.UUID;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/resources") // Базовый путь для всех методов контроллера
public class ResourceController extends SimpleBaseController<Resource, UUID> {

    public ResourceController(ResourceRepository repository) {
        super(repository);
    }
}