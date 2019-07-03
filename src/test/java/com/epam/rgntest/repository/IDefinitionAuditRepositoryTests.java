package com.epam.rgntest.repository;

import com.epam.rgntest.vo.Definition;
import com.epam.rgntest.vo.audit.DefinitionAudit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.rgntest.DictionaryUtils.generateDefinition;
import static com.epam.rgntest.DictionaryUtils.generateTerm;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
public class IDefinitionAuditRepositoryTests {

    @Autowired
    private IDefinitionAuditRepository definitionAuditRepository;
    @Autowired
    private IDefinitionRepository definitionRepository;

    @Before
    public void before() {
        definitionAuditRepository.deleteAll();
    }

    @Test
    public void shouldGetAllAuditsOfDifferentOperationsWhenFindAll() {
        List<DefinitionAudit> all = definitionAuditRepository.findAll();
        Assert.assertEquals(all.size(), 0);

        doAddModifyAndDeleteOperation();

        // then
        all = definitionAuditRepository.findAll();
        Assert.assertEquals(all.size(), 3);
    }

    @Test
    public void shouldGetParticularAuditOperationsWhenFindByOperation() {
        List<DefinitionAudit> added = definitionAuditRepository.findByOperation(DefinitionAudit.Operation.added);
        List<DefinitionAudit> modified = definitionAuditRepository.findByOperation(DefinitionAudit.Operation.modified);
        List<DefinitionAudit> deleted = definitionAuditRepository.findByOperation(DefinitionAudit.Operation.deleted);
        Assert.assertEquals(added.size(), 0);
        Assert.assertEquals(modified.size(), 0);
        Assert.assertEquals(deleted.size(), 0);

        doAddModifyAndDeleteOperation();

        added = definitionAuditRepository.findByOperation(DefinitionAudit.Operation.added);
        modified = definitionAuditRepository.findByOperation(DefinitionAudit.Operation.modified);
        deleted = definitionAuditRepository.findByOperation(DefinitionAudit.Operation.deleted);
        Assert.assertEquals(added.size(), 1);
        Assert.assertEquals(modified.size(), 1);
        Assert.assertEquals(deleted.size(), 1);
    }

    private void doAddModifyAndDeleteOperation() {
        // create -> added
        Definition def = Definition.builder().term(generateTerm()).definition(generateDefinition()).build();
        definitionRepository.save(def);
        // update -> modified
        def = def.toBuilder().definition(generateDefinition()).build();
        definitionRepository.save(def);
        // delete -> deleted
        definitionRepository.deleteByTerm(def.getTerm());
    }

}
