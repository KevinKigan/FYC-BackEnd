package com.kevingomez.FYCBackEnd.models.entity.Coches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "potencias_electricas")
public class PotenciaElectrica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "potencia_electrica_id")
    private int idPotenciaElectrica;

    @NotNull(message = "La potencia no puede estar vacia")
    private int potencia;


    public int getIdPotenciaElectrica() {
        return idPotenciaElectrica;
    }

    public void setIdPotenciaElectrica(int idPotenciaElectrica) {
        this.idPotenciaElectrica = idPotenciaElectrica;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }
}
