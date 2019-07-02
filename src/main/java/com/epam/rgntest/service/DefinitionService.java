package com.epam.rgntest.service;

import com.epam.rgntest.cache.SimpleInMemoryCache;
import com.epam.rgntest.repository.IDefinitionRepository;
import com.epam.rgntest.vo.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
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
        return cache.get(term, () -> definitionRepository.findByTerm(term));
    }

    @Override
    public Collection<Definition> getAll() {
        return StreamSupport.stream(definitionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()); // do not support cache yet
    }

    @Override
    public Definition update(Definition definition) {
        return save(definition);
    }

    @Override
    public Definition create(Definition definition) {
        return save(definition);
    }

    @Override
    public void deleteByTerm(String term) {
        definitionRepository.deleteByTerm(term);
        cache.remove(term);
    }

    @Override
    public void deleteAll() {
        definitionRepository.deleteAll();
        cache.clear();
    }

    private Definition save(Definition definition) {
        Supplier<Definition> supplier = () -> definitionRepository.save(definition);
        cache.save(definition.getTerm(), supplier);
        return cache.get(definition.getTerm(), supplier);
    }
}
