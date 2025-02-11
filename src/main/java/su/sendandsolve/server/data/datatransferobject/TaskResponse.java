package su.sendandsolve.server.data.datatransferobject;

import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.domain.Task;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record TaskResponse(
        UUID uuid,
        String title,
        String description,
        String status,
        Short priority,
        OffsetDateTime startDate,
        OffsetDateTime endDate,
        Short progress,
        UUID creatorId,
        OffsetDateTime dateCreation,
        Set<UUID> tags,
        Set<UUID> resources,
        Set<UUID> childTasks,
        Set<UUID> parentTasks){
}
