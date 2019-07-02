package com.epam.rgntest.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Audited
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "term")
public class Definition {

    @Id
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String term;

    @NotNull
    private String definition;
}
