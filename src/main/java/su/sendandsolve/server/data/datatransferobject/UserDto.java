package su.sendandsolve.server.data.datatransferobject;

import java.util.UUID;

public class UserDto implements DtoMarker{
    private UUID uuid;
    private String login;
    private String nickname;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
