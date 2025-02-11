package su.sendandsolve.server.data.datatransferobject;

import java.time.OffsetDateTime;
import java.util.UUID;

public record SessionResponse(
    UUID uuid,
    UUID userId,
    String token,
    OffsetDateTime expiryDate,
    OffsetDateTime lastActivity,
    String deviceInfo){
}
