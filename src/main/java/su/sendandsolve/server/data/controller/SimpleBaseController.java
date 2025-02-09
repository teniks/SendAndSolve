package su.sendandsolve.server.data.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class SimpleBaseController<E, ID> implements IController<E, ID> {
    protected final JpaRepository<E, ID> repository;

    public SimpleBaseController(JpaRepository<E, ID> repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<E> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public E getById(@PathVariable ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @PostMapping
    public E create(@RequestBody E entity) {
        return repository.save(entity);
    }

    @PutMapping
    public E update(@RequestBody E entity) {
        return repository.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {
        repository.deleteById(id);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<E>> createBatch(@RequestBody List<E> entities) {
        // 1. Проверка на пустой список
        if (entities == null || entities.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        // 2. Пакетное сохранение
        List<E> savedEntities = repository.saveAll(entities);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntities);
    }
}
