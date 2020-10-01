package com.osiris.osiris.controllers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class BaseCRUDController<T> {

    public CrudRepository<T, Long> repository;
    
    @GetMapping
    public List<T> getAll() {
        return (List<T>) this.repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        final Optional<T> object = this.repository.findById(id);
        if (object.isPresent()) {
            return ResponseEntity.ok(object.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object with id '" + id + "' not found");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        final Optional<T> object = this.repository.findById(id);
        if (object.isPresent()) {
            this.repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object with id '" + id + "' not found");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody T object) {
        final Optional<T> taskOpt = this.repository.findById(id);
        if (taskOpt.isPresent()) {
            T currentObject = taskOpt.get();
            this.applyChanges(currentObject, object);
            return ResponseEntity.ok(this.repository.save(currentObject));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object with id '" + id + "' not found");
    }

    @PostMapping
    public void create(@RequestBody T object) {
        this.repository.save(object);
    }
    
    public void applyChanges(T object, T request) {

    }
}
