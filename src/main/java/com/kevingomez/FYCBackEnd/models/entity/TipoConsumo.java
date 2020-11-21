package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tipos_consumos")
public class TipoConsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_consumo_id")
    private int idTipoConsumo;

    @NotNull(message = "El campo 'Tipo de consumo' no puede estar vacio")
    @Column(name = "tipo_consumo")
    private String tipoConsumo;

    public int getIdTipoConsumo() {
        return idTipoConsumo;
    }

    public void setIdTipoConsumo(int idTipoConsumo) {
        this.idTipoConsumo = idTipoConsumo;
    }

    public String getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(String tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }
}
