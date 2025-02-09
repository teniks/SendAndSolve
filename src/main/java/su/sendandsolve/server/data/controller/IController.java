package su.sendandsolve.server.data.controller;

import org.springframework.web.bind.annotation.*;

public interface IController<E, ID> {

    public Iterable<E> getAll();

    public E getById(@PathVariable ID id);

    public E create(@RequestBody E entity);

    public E update(@RequestBody E entity);

    public void delete(@PathVariable ID id);

}
