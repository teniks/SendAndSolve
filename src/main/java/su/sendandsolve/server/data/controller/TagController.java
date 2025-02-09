package su.sendandsolve.server.data.controller;

import su.sendandsolve.server.data.datatransferobject.TagResponse;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.repository.TagRepository;
import su.sendandsolve.server.data.service.IService;
import su.sendandsolve.server.data.service.TagService;

import java.util.UUID;

public class TagController extends BaseController<TagResponse, Tag, UUID> {

    public TagController(TagService service) {
        super(service);
    }
}
