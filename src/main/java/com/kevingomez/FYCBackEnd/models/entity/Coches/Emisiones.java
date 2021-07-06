package com.kevingomez.FYCBackEnd.models.entity.Coches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "emisiones")
public class Emisiones implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emisiones_id")
    private int idEmisiones;

    @NotNull(message = "Las emisiones de CO2 no puede estar vacias")
    @Column(name = "CO2")
    private int CO2;

    @NotNull(message = "Las emisiones de CO2 alternativo no puede estar vacias")
    @Column(name = "CO2_alt")
    private int CO2Alt;

    @ManyToOne(fetch = FetchType.LAZY) // Muchas emisiones pueden tener un tipo emision
    @JoinColumn(name = "tipo_emisiones_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    private TipoEmisiones tipoEmisiones;

    public int getIdEmisiones() {
        return idEmisiones;
    }

    public void setIdEmisiones(int idEmisiones) {
        this.idEmisiones = idEmisiones;
    }

    public int getCO2() {
        return CO2;
    }

    public void setCO2(int CO2) {
        this.CO2 = CO2;
    }

    public int getCO2Alt() {
        return CO2Alt;
    }

    public void setCO2Alt(int CO2Alt) {
        this.CO2Alt = CO2Alt;
    }

    public TipoEmisiones getTipoEmisiones() {
        return tipoEmisiones;
    }

    public void setTipoEmisones(TipoEmisiones tipoEmisiones) {
        this.tipoEmisiones = tipoEmisiones;
    }
}
