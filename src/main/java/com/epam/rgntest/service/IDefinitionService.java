package com.epam.rgntest.service;

import com.epam.rgntest.vo.Definition;

import java.util.Collection;
import java.util.Optional;

public interface IDefinitionService {

    Optional<Definition> getById(Long id);
    Collection<Definition> getAll();
    void deleteById(Long id);
    void deleteAll();
    Definition update(Long id, Definition definition);
    Definition create(Definition definition);

}
