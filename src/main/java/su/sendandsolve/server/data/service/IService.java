package su.sendandsolve.server.data.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface IService<D, E, ID> {
        D create(E entity);
        Collection<D> saveAll(Collection<E> entities);
        D update(E entity);
        D getById(ID id);
        Page<D> getAll(Pageable pageable);
        void delete(ID id);
        void deleteAllByIdInBatch(Collection<ID> ids);
}
