package su.sendandsolve.server.data.datatransferobject;

import org.mapstruct.Mapper;
import su.sendandsolve.server.data.domain.Task;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    default TaskResponse toTaskResponse(Task task){
        return new TaskResponse(task.getUuid(), task.getTitle(), task.getDescription(), task.getStatus(),
                task.getPriority(), task.getStartDate(), task.getEndDate(), task.getProgress(), task.getCreator().getUuid(), task.getDateCreation());
    }
    List<TaskResponse> toTaskResponseList(Collection<Task> tasks);
}
