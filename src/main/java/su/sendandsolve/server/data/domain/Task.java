package su.sendandsolve.server.data.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.time.OffsetDateTime;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", columnDefinition = "UUID")
    private UUID uuid;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", length = 64)
    private String status;

    @Column(name = "priority")
    private Short priority;

    @Column(name = "startdate")
    private OffsetDateTime startDate;

    @Column(name = "enddate")
    private OffsetDateTime endDate;

    @Column(name = "progress")
    private Short progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creator", referencedColumnName = "uuid")
    private User creator;

    @Column(name = "date_creation")
    private OffsetDateTime dateCreation;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted = false;

    @Column(name = "is_synced", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isSynced = false;


    // Связь ManyToMany с Tag через таблицу TaskTags
    @ManyToMany
    @JoinTable(
            name = "tasktags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    // Связь с дочерними задачами через TaskHierarchy
    @ManyToMany
    @JoinTable(
            name = "taskhierarchy",
            joinColumns = @JoinColumn(name = "id_parent"),
            inverseJoinColumns = @JoinColumn(name = "id_child")
    )
    private Set<Task> childTasks = new HashSet<>();

    // Обратная связь для родительских задач
    @ManyToMany(mappedBy = "childTasks")
    private Set<Task> parentTasks = new HashSet<>();

    // Связь с Resources через TaskResource
    @ManyToMany
    @JoinTable(
            name = "taskresource",
            joinColumns = @JoinColumn(name = "id_task"),
            inverseJoinColumns = @JoinColumn(name = "id_resource")
    )
    private Set<Resource> resources = new HashSet<>();


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public Short getProgress() {
        return progress;
    }

    public void setProgress(Short progress) {
        this.progress = progress;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public OffsetDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(OffsetDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(Boolean isSynced) {
        this.isSynced = isSynced;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public void addTags(Set<Tag> tags){
        this.tags.addAll(tags);
    }

    public Set<Task> getChildTasks() {
        return childTasks;
    }

    public void setChildTasks(Set<Task> childTasks) {
        this.childTasks = childTasks;
    }

    public void addChildTask(Task task){
        this.childTasks.add(task);
    }

    public void addChiledsTask(Set<Task> childTasks){
        this.childTasks.addAll(childTasks);
    }

    public Set<Task> getParentTasks() {
        return parentTasks;
    }

    public void setParentTasks(Set<Task> parentTasks) {
        this.parentTasks = parentTasks;
    }

    public void addParentTask(Task task){
        this.parentTasks.add(task);
    }

    public void addParentTasks(Set<Task> parentTasks){
        this.parentTasks.addAll(parentTasks);
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public void addResource(Resource resource){
        this.resources.add(resource);
    }

    public void addResources(Set<Resource> resources){
        this.resources.addAll(resources);
    }
}