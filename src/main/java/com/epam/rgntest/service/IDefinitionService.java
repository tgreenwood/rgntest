package com.epam.rgntest.service;

import com.epam.rgntest.vo.Definition;

import java.util.Collection;

public interface IDefinitionService {

    Definition getByTerm(String term);
    Collection<Definition> getAll();
    void deleteByTerm(String term);
    void deleteAll();
    Definition update(Definition definition);
    Definition create(Definition definition);

}
