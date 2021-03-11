package com.kevingomez.FYCBackEnd.models.entity.Coches;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "sobrealimentaciones")
public class Sobrealimentacion  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sobrealimentacion_id")
    private int idSobrealimentacion;

    @NotNull(message = "El campo turbo no puede estar vacio")
    private boolean turbo;

    @NotNull(message = "El campo supercargador no puede estar vacio")
    private boolean supercargador;


    public int getIdSobrealimentacion() {
        return idSobrealimentacion;
    }

    public void setIdSobrealimentacion(int idSobrealimentacion) {
        this.idSobrealimentacion = idSobrealimentacion;
    }

    public boolean isTurbo() {
        return turbo;
    }

    public void setTurbo(boolean turbo) {
        this.turbo = turbo;
    }

    public boolean isSupercargador() {
        return supercargador;
    }

    public void setSupercargador(boolean supercargador) {
        this.supercargador = supercargador;
    }

}
