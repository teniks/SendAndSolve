package su.sendandsolve.server.data.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import su.sendandsolve.server.data.exception.AlreadyExistsException;

import java.util.Collection;

public interface IService<D, E, ID> {
        D create(D dtos) throws AlreadyExistsException;
        Collection<D> saveAll(Collection<D> dtos) throws AlreadyExistsException;
        D update(D dtos) throws EntityNotFoundException;
        D getById(ID id) throws EntityNotFoundException;
        Collection<D> getAll(Pageable pageable);
        void delete(ID id);
        void deleteAllByIdInBatch(Collection<ID> ids);
}
