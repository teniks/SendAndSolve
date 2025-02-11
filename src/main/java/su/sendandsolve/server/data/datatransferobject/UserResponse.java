package su.sendandsolve.server.data.datatransferobject;

import java.util.UUID;

public record UserResponse(
    UUID uuid,
    String login,
    String nickname,
    String password){

}
