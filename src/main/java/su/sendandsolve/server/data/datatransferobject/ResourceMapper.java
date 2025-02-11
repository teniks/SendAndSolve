package su.sendandsolve.server.data.datatransferobject;

import org.mapstruct.Mapper;
import su.sendandsolve.server.data.domain.Resource;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResourceMapper {
    default ResourceResponse toResourceResponse(Resource resource){
        return new ResourceResponse(resource.getUuid(), resource.getCreator().getUuid(), resource.getUploadTimestamp(),
                resource.getByteSize(), resource.getHash(), resource.getFileLocation(), resource.getMetadata());
    }
    List<ResourceResponse> toResourceResponseList(List<Resource> resources);
}
