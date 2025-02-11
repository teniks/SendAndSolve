package su.sendandsolve.server.data.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.exception.AlreadyExistsException;
import su.sendandsolve.server.data.service.IService;

import java.util.Collection;
import java.util.List;

public abstract class BaseController<D, E , ID>{
    protected final IService<D, E, ID> service;

    public BaseController(IService<D, E, ID> service){
        this.service = service; // Внедрение зависимости через конструктор
    }

    @GetMapping
    public Collection<D> getAll(@PageableDefault Pageable pageable) { // GET /api/entities → список всех сущностей
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable ID id){ // GET /api/entities/{id} → получение сущности по UUID
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<D> create(@Validated @RequestBody D dto) { // POST /api/entities → создание новой сущности
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<D> update(@PathVariable ID id,@Validated @RequestBody D dto) { // PUT /api/entities → обновление сущности entity) { // PUT /api/entities → обновление сущности
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(dto));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.OK) // или HttpStatus.NO_CONTENT если не нужно возвращать данные
    public ResponseEntity<Void> delete(@PathVariable ID id) {  // DELETE /api/entity/{id} → удаление сущности по id
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/batch")
    @Transactional
    public ResponseEntity<List<D>> createBatch(@Validated @RequestBody List<D> dtos, @RequestParam(defaultValue = "100") int batchSize) {
        if (dtos == null || dtos.isEmpty()) return ResponseEntity.badRequest().build();

        try {
            List<D> savedDtos = (List<D>) service.saveAll(dtos);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDtos);
        }catch (AlreadyExistsException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
