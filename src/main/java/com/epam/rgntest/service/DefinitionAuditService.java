package com.epam.rgntest.service;

import com.epam.rgntest.repository.IDefinitionAuditRepository;
import com.epam.rgntest.vo.audit.DefinitionAudit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DefinitionAuditService implements IDefinitionAuditService {

    private IDefinitionAuditRepository definitionAuditRepository;

    @Autowired
    public DefinitionAuditService(IDefinitionAuditRepository definitionAuditRepository) {
        this.definitionAuditRepository = definitionAuditRepository;
    }

    @Override
    public List<DefinitionAudit> getAllAudits() {
        log.debug("Getting all defition's audits");
        return definitionAuditRepository.findAll();
    }

    @Override
    public List<DefinitionAudit> getAuditsByOperation(DefinitionAudit.Operation operation) {
        log.debug("Getting definitions by operation \"" + operation + "\"");
        return definitionAuditRepository.findByOperation(operation);
    }

}
