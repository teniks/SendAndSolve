package su.sendandsolve.server.data.datatransferobject;

import java.time.OffsetDateTime;
import java.util.UUID;

public record NoteResponse(
    UUID uuid,
    String title,
    String content,
    OffsetDateTime creationDate,
    UUID userId){
}
