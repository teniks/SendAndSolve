package su.sendandsolve.server.data.service;

import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.repository.TagRepository;

import java.util.UUID;

public class TagController extends SimpleBaseController<Tag, UUID>{

    public TagController(TagRepository repository) {
        super(repository);
    }
}
