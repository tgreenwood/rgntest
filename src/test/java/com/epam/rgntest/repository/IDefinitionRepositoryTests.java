package com.epam.rgntest.repository;

import com.epam.rgntest.vo.Definition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.epam.rgntest.DictionaryUtils.generateDefinition;
import static com.epam.rgntest.DictionaryUtils.generateTerm;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class IDefinitionRepositoryTests {

    @Autowired
    private IDefinitionRepository definitionRepository;

    @Before
    public void before() {
        deleteAll();
    }

    @Test
    public void shouldGetTwoRowsWhenFindAll() {
        List<Definition> allDefinitions = convertIterableToList(definitionRepository.findAll());
        Assert.assertEquals(allDefinitions.size(), 0);

        generateAndCreateOne();
        generateAndCreateOne();

        allDefinitions = convertIterableToList(definitionRepository.findAll());

        Assert.assertEquals(allDefinitions.size(), 2);
    }

    @Test
    public void shouldGetOneRowWhenGetOneByTerm() {
        String generatedTerm = generateTerm();
        String generatedDef = generateDefinition();
        Definition newDefinition = createOne(generatedTerm, generatedDef);

        Definition definition = definitionRepository.findByTerm(newDefinition.getTerm());

        Assert.assertEquals(definition.getTerm(), generatedTerm);
        Assert.assertEquals(definition.getDefinition(), generatedDef);
    }

    @Test
    public void shouldAddDefinitionWhenCreate() {
        Definition newDefinition = generateAndCreateOne();

        Definition result = definitionRepository.findByTerm(newDefinition.getTerm());
        Assert.assertEquals(newDefinition.getTerm(), result.getTerm());
        Assert.assertEquals(newDefinition.getDefinition(), result.getDefinition());
    }

    @Test
    public void shouldUpdateFieldWhenSaveWithUpdates() {
        Definition initialDefinition = generateAndCreateOne();

        final String updatedDef = generateDefinition();
        Definition updatedDefinition = initialDefinition.toBuilder()
                .definition(updatedDef)
                .build();
        definitionRepository.save(updatedDefinition);

        updatedDefinition = definitionRepository.findByTerm(initialDefinition.getTerm());
        Assert.assertEquals(updatedDef, updatedDefinition.getDefinition());
    }

    @Test
    public void shouldDeleteDefinitionWhenDeleteByTerm() {
        Definition definition = generateAndCreateOne();
        List<Definition> allDefsBeforeDelete = convertIterableToList(definitionRepository.findAll());

        definitionRepository.deleteByTerm(definition.getTerm());

        List<Definition> allDefsAfterDelete = convertIterableToList(definitionRepository.findAll());
        Assert.assertEquals(allDefsBeforeDelete.size() - 1, allDefsAfterDelete.size());
        Assert.assertNull(definitionRepository.findByTerm(definition.getTerm()));

    }

    @Test
    public void shouldDeleteAllDefinitionsWhenDeleteAll() {
        Definition definition1 = generateAndCreateOne();
        Definition definition2 = generateAndCreateOne();
        Assert.assertEquals(convertIterableToList(definitionRepository.findAll()).size(), 2);

        definitionRepository.deleteAll();

        Assert.assertEquals(convertIterableToList(definitionRepository.findAll()).size(), 0);

    }


    private List<Definition> convertIterableToList(Iterable<Definition> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    private void deleteAll() {
        definitionRepository.deleteAll();
    }

    private Definition createOne(String term, String definition) {
        Definition newDefinition = Definition.builder()
                .term(term)
                .definition(definition)
                .build();

        return definitionRepository.save(newDefinition);
    }

    private Definition generateAndCreateOne() {
        return createOne(generateTerm(), generateDefinition());
    }
}
