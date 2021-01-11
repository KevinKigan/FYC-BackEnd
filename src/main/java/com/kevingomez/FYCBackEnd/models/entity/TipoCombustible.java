package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "tipos_combustibles")
public class TipoCombustible implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_combustible_id")
    private int idTipoCombustible;

    @Column(name = "tipo_combustible")
    @NotNull(message = "El tipo de combustible no puede estar vacio")
    private String tipoCombustible;

    public int getIdCombustible() {
        return idTipoCombustible;
    }

    public void setIdCombustible(int idTipoCombustible) {
        this.idTipoCombustible = idTipoCombustible;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

}
