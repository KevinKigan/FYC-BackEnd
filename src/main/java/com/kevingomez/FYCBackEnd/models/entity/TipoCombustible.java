package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipos_combustibles")
public class TipoCombustible implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combustible_id")
    private int idCombustible;

    private String combustible;

    public int getIdCombustible() {
        return idCombustible;
    }

    public void setIdCombustible(int idCombustible) {
        this.idCombustible = idCombustible;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

}
