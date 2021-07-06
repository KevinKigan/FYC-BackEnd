package com.kevingomez.FYCBackEnd.models.entity.Coches;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "consumos_electricos")
public class ConsumoElectrico  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumo_electrico_id")
    private int idConsumoElectrico;

    @NotNull(message = "El consumo de ciudad no puede estar vacio")
    private double ciudad;

    @NotNull(message = "El consumo combinado no puede estar vacio")
    private double combinado;

    @NotNull(message = "El consumo de autopista no puede estar vacio")
    private double autopista;

    public int getIdConsumoElectrico() {
        return idConsumoElectrico;
    }

    public void setIdConsumoElectrico(int idConsumoElectrico) {
        this.idConsumoElectrico = idConsumoElectrico;
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
