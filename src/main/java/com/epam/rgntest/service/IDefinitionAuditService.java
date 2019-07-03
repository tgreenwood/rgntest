package com.epam.rgntest.service;

import com.epam.rgntest.vo.audit.DefinitionAudit;

import java.util.List;

public interface IDefinitionAuditService {

    List<DefinitionAudit> getAllAudits();
    List<DefinitionAudit> getAuditsByOperation(DefinitionAudit.Operation operation);
}
