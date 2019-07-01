package com.epam.rgntest.repository;

import com.epam.rgntest.vo.Definition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDefinitionRepository extends CrudRepository<Definition, Long> {
}
