package su.sendandsolve.server.data.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.sendandsolve.server.data.datatransferobject.TagResponse;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.service.TagService;

import java.util.UUID;

@RestController // Помечаем класс как контроллер, обрабатывающий HTTP-запросы
@RequestMapping("/api/tags") // Базовый путь для всех методов контроллера
public class TagController extends BaseController<TagResponse, Tag, UUID> {

    public TagController(TagService service) {
        super(service);
    }
}
