package su.sendandsolve.server.data.datatransferobject;

import org.mapstruct.Mapper;
import su.sendandsolve.server.data.domain.Note;
import su.sendandsolve.server.data.domain.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    default NoteResponse toNoteResponse(Note note){
        return new NoteResponse(note.getUuid(), note.getTitle(), note.getDescription(), note.getDateCreation(), note.getUser().getUuid(), note.getTags().stream().map(Tag::getUuid).collect(Collectors.toSet()));
    }
    List<NoteResponse> toNoteResponseList(List<Note> notes);
}
