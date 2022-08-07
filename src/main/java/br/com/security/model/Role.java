package br.com.security.model;


import io.micronaut.data.annotation.GeneratedValue;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60,unique = true)
    private RoleType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }
}