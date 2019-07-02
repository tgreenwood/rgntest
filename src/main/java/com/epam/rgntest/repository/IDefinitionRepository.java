package com.epam.rgntest.repository;

import com.epam.rgntest.vo.Definition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IDefinitionRepository extends CrudRepository<Definition, String> {

    @Transactional
    Definition findByTerm(String term);
    @Transactional
    void deleteByTerm(String term);
}
