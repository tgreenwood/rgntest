package com.epam.rgntest.controller;

import com.epam.rgntest.service.DefinitionAuditService;
import com.epam.rgntest.vo.audit.DefinitionAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/definition/audit")
public class DefinitionAuditRevisionController {

    private DefinitionAuditService definitionAuditService;

    @Autowired
    public DefinitionAuditRevisionController(DefinitionAuditService definitionAuditService) {
        this.definitionAuditService = definitionAuditService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DefinitionAudit>> getAll() {
        return ResponseEntity.ok(definitionAuditService.getAllAudits());
    }

    @GetMapping("/operation/{operation}")
    public ResponseEntity<List<DefinitionAudit>> getByOperation(@PathVariable DefinitionAudit.Operation operation) {
        return ResponseEntity.ok(definitionAuditService.getAuditsByOperation(operation));
    }

}
