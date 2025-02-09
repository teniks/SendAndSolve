package su.sendandsolve.server.data.controller;

import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.datatransferobject.ResourceResponse;
import su.sendandsolve.server.data.domain.Resource;
import su.sendandsolve.server.data.repository.ResourceRepository;
import su.sendandsolve.server.data.service.IService;
import su.sendandsolve.server.data.service.ResourceService;

import java.util.UUID;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/resources") // Базовый путь для всех методов контроллера
public class ResourceController extends BaseController<ResourceResponse, Resource, UUID> {

    public ResourceController(ResourceService service) {
        super(service);
    }
}