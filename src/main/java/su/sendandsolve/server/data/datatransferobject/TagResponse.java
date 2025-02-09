package su.sendandsolve.server.data.datatransferobject;

import java.util.UUID;

public record TagResponse(
    UUID uuid,
    String name){
}
