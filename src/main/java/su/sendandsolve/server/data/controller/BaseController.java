package su.sendandsolve.server.data.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.service.IService;

import java.util.List;

public abstract class BaseController<D, E , ID>{
    protected final IService<D, E, ID> service;

    public BaseController(IService<D, E, ID> service){
        this.service = service; // Внедрение зависимости через конструктор
    }

    @GetMapping
    public Page<D> getAll(@PageableDefault Pageable pageable) { // GET /api/entities → список всех сущностей
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public D getById(@PathVariable ID id){ // GET /api/entities/{id} → получение сущности по UUID
        return service.getById(id);
    }

    @PostMapping
    public D create(@Validated @RequestBody E entity) { // POST /api/entities → создание новой сущности
        return service.create(entity);
    }

    @PutMapping
    public D update(@Validated @RequestBody E entity) { // PUT /api/entities → обновление сущности entity) { // PUT /api/entities → обновление сущности
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // или HttpStatus.NO_CONTENT если не нужно возвращать данные
    public ResponseEntity<Void> delete(@PathVariable ID id) {  // DELETE /api/entity/{id} → удаление сущности по id
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/batch")
    @Transactional
    public ResponseEntity<List<D>> createBatch(@Validated @RequestBody List<E> entity, @RequestParam(defaultValue = "100") int batchSize) {
        if (entity == null || entity.isEmpty()) return ResponseEntity.badRequest().build();

        List<D> savedDtos = (List<D>) service.saveAll(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDtos);
    }
}
