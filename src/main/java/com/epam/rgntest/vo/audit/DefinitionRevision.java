package com.epam.rgntest.vo.audit;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "REVINFO")
@Getter
public class DefinitionRevision implements Serializable {

    @Id
    private int rev;

    @OneToOne(mappedBy = "revision", cascade = CascadeType.ALL)
    @JsonIgnore
    private DefinitionAudit definitionAudit;

    // for a reason @Temporal does not work - cannot parse 13 chars length
    // using getDate() to serialize instead
    @Column(name = "revtstmp")
    @JsonIgnore
    private long timestamp;

    @Transient
    @JsonGetter(value = "datetime")
    public Date getDate() {
        return new Date(timestamp);
    }

}
