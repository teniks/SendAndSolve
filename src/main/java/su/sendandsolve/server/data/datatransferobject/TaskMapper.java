package su.sendandsolve.server.data.datatransferobject;

import org.mapstruct.Mapper;
import su.sendandsolve.server.data.domain.Resource;
import su.sendandsolve.server.data.domain.Tag;
import su.sendandsolve.server.data.domain.Task;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    default TaskResponse toTaskResponse(Task task){
        return new TaskResponse(task.getUuid(), task.getTitle(), task.getDescription(), task.getStatus(),
                task.getPriority(), task.getStartDate(), task.getEndDate(), task.getProgress(), task.getCreator().getUuid(),
                task.getDateCreation(),
                task.getTags().stream().map(Tag::getUuid).collect(Collectors.toSet()),
                task.getResources().stream().map(Resource::getUuid).collect(Collectors.toSet()),
                task.getChildTasks().stream().map(Task::getUuid).collect(Collectors.toSet()),
                task.getParentTasks().stream().map(Task::getUuid).collect(Collectors.toSet()));
    }
    List<TaskResponse> toTaskResponseList(Collection<Task> tasks);
}
