package com.epam.rgntest.service;

import com.epam.rgntest.cache.SimpleInMemoryCache;
import com.epam.rgntest.repository.IDefinitionRepository;
import com.epam.rgntest.vo.Definition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class DefinitionService implements IDefinitionService {

    private IDefinitionRepository definitionRepository;
    private SimpleInMemoryCache<String, Definition> cache;

    @Autowired
    public DefinitionService(IDefinitionRepository definitionRepository) {
        this.definitionRepository = definitionRepository;
        this.cache = new SimpleInMemoryCache<>();
    }

    @Override
    public Definition getByTerm(String term) {
        log.debug("Getting definition by term \"" + term + "\"");
        return cache.get(term, () -> definitionRepository.findByTerm(term));
    }

    @Override
    public Collection<Definition> getAll() {
        log.debug("Getting all definitions");
        return StreamSupport.stream(definitionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()); // do not support cache yet
    }

    @Override
    public Definition update(Definition definition) {
        log.debug("Updating definition to \"" + definition.getDefinition()
                + "\" for term \"" + definition.getTerm() + "\"");
        return save(definition);
    }

    @Override
    public Definition create(Definition definition) {
        log.debug("Creating definition: " + definition);
        return save(definition);
    }

    @Override
    public void deleteByTerm(String term) {
        log.debug("Deleting definition by term \"" + term + "\"");
        definitionRepository.deleteByTerm(term);
        cache.remove(term);
    }

    @Override
    public void deleteAll() {
        log.debug("Getting all definitions");
        definitionRepository.deleteAll();
        cache.clear();
    }

    private Definition save(Definition definition) {
        Supplier<Definition> supplier = () -> definitionRepository.save(definition);
        cache.save(definition.getTerm(), supplier);
        return cache.get(definition.getTerm(), supplier);
    }
}
