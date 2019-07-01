package com.epam.rgntest.controller;

import com.epam.rgntest.service.DefinitionService;
import com.epam.rgntest.service.IDefinitionService;
import com.epam.rgntest.vo.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/definition")
public class DefinitionController {

    private IDefinitionService definitionService;

    @Autowired
    public DefinitionController(DefinitionService definitionService) {
        this.definitionService = definitionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Definition> get(@PathVariable Long id) {
        return definitionService.getById(id).isPresent()
                ? ResponseEntity.ok(definitionService.getById(id).get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Definition>> get() {
        return ResponseEntity.ok(definitionService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        definitionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll() {
        definitionService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Definition> update(@PathVariable Long id, @RequestBody Definition definition) {
        Assert.notNull(id, "The given id must not be null!");
        return ResponseEntity.ok(definitionService.update(id, definition));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Definition definition) {
        Definition newDefinition = definitionService.create(definition);
        return ResponseEntity.created(URI.create("/definition/" + newDefinition.getId())).build();
    }
}
