package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "consumos_alternativos")
public class ConsumoAlternativo  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumo_alternativo_id")
    private int idConsumoAlternativo;

    @NotNull(message = "El consumo de ciudad no puede estar vacio")
    private double ciudad;

    @NotNull(message = "El consumo combinado no puede estar vacio")
    private double combinado;

    @NotNull(message = "El consumo de autopista no puede estar vacio")
    private double autopista;

    public int getIdConsumoAlternativo() {
        return idConsumoAlternativo;
    }

    public void setIdConsumoAlternativo(int idConsumoAlternativo) {
        this.idConsumoAlternativo = idConsumoAlternativo;
    }

    public double getCiudad() {
        return ciudad;
    }

    public void setCiudad(double ciudad) {
        this.ciudad = ciudad;
    }

    public double getCombinado() {
        return combinado;
    }

    public void setCombinado(double combinado) {
        this.combinado = combinado;
    }

    public double getAutopista() {
        return autopista;
    }

    public void setAutopista(double autopista) {
        this.autopista = autopista;
    }
}
