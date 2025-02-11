package su.sendandsolve.server.data.domain;

import java.time.OffsetDateTime;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @Column(name = "uuid", columnDefinition = "UUID")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creator", referencedColumnName = "uuid")
    private User creator;

    @Column(name = "upload_timestamp")
    private OffsetDateTime uploadTimestamp;

    @Column(name = "byte_size")
    private Long byteSize;

    @Column(name = "hash", length = 256)
    private String hash;

    @Column(name = "file_location", columnDefinition = "TEXT")
    private String fileLocation;

    @Column(name = "metadata", columnDefinition = "JSON")
    private String metadata;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted = false;

    @Column(name = "is_synced", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isSynced = false;

    @ManyToMany(mappedBy = "resources")
    private Set<Task> tasks = new HashSet<>();

    // Геттеры и сеттеры
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public OffsetDateTime getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(OffsetDateTime uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }

    public Long getByteSize() {
        return byteSize;
    }

    public void setByteSize(Long byteSize) {
        this.byteSize = byteSize;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void addTasks(Set<Task> tasks){
        this.tasks.addAll(tasks);
    }
}