package com.kevingomez.FYCBackEnd.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "combustibles")
public class Combustible  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combustible_id")
    private int idCombustible;

    @ManyToOne(fetch = FetchType.LAZY) // Muchos combustibles pueden tener un tipo combustible
    @JoinColumn(name = "tipo_combustible_normal_id")
    @JsonIgnoreProperties(value={"idCombustible","hibernateLazyInitializer","handler"}, allowSetters = true)
    private TipoCombustible tipoCombustibleNormal;

    @ManyToOne(fetch = FetchType.LAZY) // Muchos combustibles pueden tener un tipo combustible
    @JoinColumn(name = "tipo_combustible_alternativo_id")
    @JsonIgnoreProperties(value={"idCombustible","hibernateLazyInitializer","handler"}, allowSetters = true)
    private TipoCombustible tipoCombustibleAlternativo;


    public int getIdCombustible() {
        return idCombustible;
    }

    public void setIdCombustible(int idCombustible) {
        this.idCombustible = idCombustible;
    }

    public TipoCombustible getTipoCombustibleNormal() {
        return tipoCombustibleNormal;
    }

    public void setTipoCombustibleNormal(TipoCombustible tipoCombustibleNormal) {
        this.tipoCombustibleNormal = tipoCombustibleNormal;
    }

    public TipoCombustible getTipoCombustibleAlternativo() {
        return tipoCombustibleAlternativo;
    }

    public void setTipoCombustibleAlternativo(TipoCombustible tipoCombustibleAlternativo) {
        this.tipoCombustibleAlternativo = tipoCombustibleAlternativo;
    }
}
