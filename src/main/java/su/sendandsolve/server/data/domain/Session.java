package su.sendandsolve.server.data.domain;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @Column(name = "uuid", columnDefinition = "UUID")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser", referencedColumnName = "uuid")
    private User user;

    @Column(name = "user_token", length = 1024)
    private String userToken;

    @Column(name = "expiry_date")
    private OffsetDateTime expiryDate;

    @Column(name = "date_activity")
    private OffsetDateTime dateActivity;

    @Column(name = "device", length = 256)
    private String device;

    // Геттеры и сеттеры
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OffsetDateTime getDateActivity() {
        return dateActivity;
    }

    public void setDateActivity(OffsetDateTime dateActivity) {
        this.dateActivity = dateActivity;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public OffsetDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(OffsetDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}