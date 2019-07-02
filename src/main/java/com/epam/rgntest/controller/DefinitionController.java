package com.epam.rgntest.controller;

import com.epam.rgntest.service.DefinitionService;
import com.epam.rgntest.service.IDefinitionService;
import com.epam.rgntest.vo.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @GetMapping("/{term}")
    public ResponseEntity<Definition> get(@PathVariable String term) {
        Definition definition = definitionService.getByTerm(term);
        return  definition != null
                ? ResponseEntity.ok(definition)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Definition>> get() {
        return ResponseEntity.ok(definitionService.getAll());
    }

    @DeleteMapping("/{term}")
    public ResponseEntity<?> delete(@PathVariable String term) {
        definitionService.deleteByTerm(term);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll() {
        definitionService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Definition> update(@RequestBody @Valid Definition definition) {
        return ResponseEntity.ok(definitionService.update(definition));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Definition definition) {
        Definition newDefinition = definitionService.create(definition);
        return ResponseEntity.created(URI.create("/definition/" + newDefinition.getTerm())).build();
    }
}
