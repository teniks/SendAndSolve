package su.sendandsolve.server.data.datatransferobject;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ResourceResponse(
    UUID uuid,
    UUID creatorId,
    OffsetDateTime uploadTime,
    Long size,
    String hash,
    String filePath){
}
