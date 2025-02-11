package su.sendandsolve.server.data.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @Column(name = "uuid", columnDefinition = "UUID")
    private UUID uuid;

    @Column(name = "title", length = 256)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creator", referencedColumnName = "uuid")
    private User creator;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted = false;

    @Column(name = "is_synced", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isSynced = false;


    @ManyToMany
    @JoinTable(
            name = "teammembers",
            joinColumns = @JoinColumn(name = "id_team"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Set<User> members = new HashSet<>();


    // Геттеры и сеттеры
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getSynced() {
        return isSynced;
    }

    public void setSynced(Boolean synced) {
        isSynced = synced;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public void setMember(User user) {
        this.members.add(user);
    }

    public Set<UUID> getMembersUuids() {
        return members.stream().map(User::getUuid).collect(Collectors.toSet());
    }
}