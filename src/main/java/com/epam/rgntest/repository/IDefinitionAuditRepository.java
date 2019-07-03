package com.epam.rgntest.repository;

import com.epam.rgntest.vo.audit.DefinitionAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IDefinitionAuditRepository extends JpaRepository<DefinitionAudit, DefinitionAudit.DefinitionAuditPk> {

    @Transactional
    List<DefinitionAudit> findByOperation(DefinitionAudit.Operation operation);

}
