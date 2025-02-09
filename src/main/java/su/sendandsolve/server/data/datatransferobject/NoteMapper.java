package su.sendandsolve.server.data.datatransferobject;

import org.mapstruct.Mapper;
import su.sendandsolve.server.data.domain.Note;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    default NoteResponse toNoteResponse(Note note){
        return new NoteResponse(note.getUuid(), note.getTitle(), note.getDescription(), note.getDateCreation(), note.getUser().getUuid());
    }
    List<NoteResponse> toNoteResponseList(List<Note> notes);
}
