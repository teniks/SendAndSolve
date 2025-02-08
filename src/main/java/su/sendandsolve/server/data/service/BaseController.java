package su.sendandsolve.server.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su.sendandsolve.server.data.datatransferobject.DtoMarker;
import su.sendandsolve.server.data.datatransferobject.GenericMapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseController<D extends DtoMarker, E , ID>{
    protected final JpaRepository<E, ID> repository;
    protected final GenericMapper<D, E> mapper;

    public BaseController(JpaRepository<E, ID> repository, GenericMapper<D, E> mapper){
        this.repository = repository; // Внедрение зависимости через конструктор
        this.mapper = mapper;
    }

    @GetMapping
    public Iterable<D> getAll() { // GET /api/entities → список всех сущностей
        var result = repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
        return result;
    }

    @GetMapping("/{id}")
    public D getOne(@PathVariable ID id){ // GET /api/entities/{id} → получение сущности по UUID
        E entity = repository.getReferenceById(id);
        return mapper.toDto(entity);
    }

    @PostMapping
    public D create(@RequestBody E entity) { // POST /api/entities → создание новой сущности
        E savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @PutMapping
    public D update(@RequestBody D dto) { // PUT /api/entities → обновление сущности entity) { // PUT /api/entities → обновление сущности
        E entity = mapper.toEntity(dto);
        E updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // или HttpStatus.NO_CONTENT если не нужно возвращать данные
    public ResponseEntity<Void> delete(@PathVariable ID id) {  // DELETE /api/entity/{id} → удаление сущности по id
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<List<D>> createBatch(@RequestBody List<D> dtos) {
        // 1. Проверка на пустой список
        if (dtos == null || dtos.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // 2. Преобразование DTO в сущности
        List<E> entities = dtos.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());

        // 3. Пакетное сохранение
        List<E> savedEntities = repository.saveAll(entities);

        // 4. Преобразование обратно в DTO
        List<D> savedDtos = savedEntities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDtos);
    }
}
