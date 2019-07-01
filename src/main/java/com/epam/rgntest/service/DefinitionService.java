package com.epam.rgntest.service;

import com.epam.rgntest.repository.IDefinitionRepository;
import com.epam.rgntest.vo.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DefinitionService implements IDefinitionService {

    private IDefinitionRepository definitionRepository;

    @Autowired
    public DefinitionService(IDefinitionRepository definitionRepository) {
        this.definitionRepository = definitionRepository;
    }


    public Optional<Definition> getById(Long id) {
        return definitionRepository.findById(id);
    }

    public Collection<Definition> getAll() {
        return StreamSupport.stream(definitionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        definitionRepository.deleteById(id);
    }

    public void deleteAll() {
        definitionRepository.deleteAll();
    }

    public Definition update(Long id, Definition definition) {
        Definition definitionToUpdate = definition.toBuilder()
                .id(id)
                .build();
        return definitionRepository.save(definitionToUpdate);
    }

    public Definition create(Definition definition) {
        return definitionRepository.save(definition);
    }
}
