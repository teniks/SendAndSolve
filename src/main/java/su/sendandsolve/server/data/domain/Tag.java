package su.sendandsolve.server.data.domain;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @Column(name = "uuid", columnDefinition = "UUID")
    private UUID uuid;

    @Column(name = "tag", unique = true, nullable = false, length = 256)
    private String name;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted = false;

    @Column(name = "is_synced", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isSynced = false;

    @ManyToMany(mappedBy = "tags")
    private Set<Note> notes;

    @ManyToMany(mappedBy = "tags")
    private Set<Task> tasks;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
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

    public void addNote(Note note){
        this.notes.add(note);
    }

    public void removeNote(Note note){
        this.notes.remove(note);
    }

    public void removeTask(Task task){
        this.tasks.remove(task);
    }

    public void addTasks(Set<Task> tasks){
        this.tasks.addAll(tasks);
    }

    public void addNotes(Set<Note> notes){
        this.notes.addAll(notes);
    }
}
