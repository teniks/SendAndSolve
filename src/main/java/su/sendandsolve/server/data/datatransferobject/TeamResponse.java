package su.sendandsolve.server.data.datatransferobject;

import java.util.UUID;

public record TeamResponse(
    UUID uuid,
    String title,
    UUID creatorId){
}
