package com.epam.rgntest.vo.audit;

import lombok.Data;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "DEFINITION_AUD")
@Getter
public class DefinitionAudit implements Serializable {

    @EmbeddedId
    private DefinitionAuditPk definitionAuditPk;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "revtype")
    private Operation operation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rev", insertable = false, updatable = false)
    private DefinitionRevision revision;
    private String definition;

    @Data
    @Embeddable
    public static class DefinitionAuditPk implements Serializable {
        private int rev;
        private String term;
    }

    public enum Operation {
        added,
        modified,
        deleted;
    }
}
