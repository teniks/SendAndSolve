package su.sendandsolve.server.data.datatransferobject;

import java.time.OffsetDateTime;
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
    OffsetDateTime dateCreation){
}
