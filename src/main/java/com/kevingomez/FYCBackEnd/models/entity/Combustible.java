package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "combustibles")
public class Combustible  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combustible_id")
    private int idCombustible;

    @Column(name = "combustible_normal")
    private String combustibleNormal;

    @Column(name = "combustible_alternativo")
    private String combustibleAlternativo;

    public int getIdCombustible() {
        return idCombustible;
    }

    public void setIdCombustible(int idCombustible) {
        this.idCombustible = idCombustible;
    }

    public String getCombustibleNormal() {
        return combustibleNormal;
    }

    public void setCombustibleNormal(String combustibleNormal) {
        this.combustibleNormal = combustibleNormal;
    }

    public String getCombustibleAlternativo() {
        return combustibleAlternativo;
    }

    public void setCombustibleAlternativo(String combustibleAlternativo) {
        this.combustibleAlternativo = combustibleAlternativo;
    }

}
