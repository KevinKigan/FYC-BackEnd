package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tipo_emisiones")
public class TipoEmisiones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_emisiones_id")
    private int idTipoEmisiones;

    @NotNull(message = "El tipo de emisiones no puede estar vacio")
    @Column(name = "tipo_emisiones")
    private String tipoEmisiones;

    public int getIdTipoEmisiones() {
        return idTipoEmisiones;
    }

    public void setIdTipoEmisiones(int idTipoEmisiones) {
        this.idTipoEmisiones = idTipoEmisiones;
    }

    public String getTipoEmisiones() {
        return tipoEmisiones;
    }

    public void setTipoEmisiones(String tipoEmisiones) {
        this.tipoEmisiones = tipoEmisiones;
    }
}
