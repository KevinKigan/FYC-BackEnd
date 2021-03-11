package com.kevingomez.FYCBackEnd.models.entity.Coches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "consumos")
public class Consumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumo_id")
    private int idConsumo;

    @OneToOne(fetch = FetchType.LAZY) // Un consumo tiene un consumo normal
    @JoinColumn(name = "consumo_normal_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    private ConsumoNormal idConsumoNormal;

    @OneToOne(fetch = FetchType.LAZY) // Un consumo tiene un consumo alternativo
    @JoinColumn(name = "consumo_alternativo_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    private ConsumoAlternativo idConsumoAlternativo;

    @OneToOne(fetch = FetchType.LAZY) // Un consumo tiene un consumo electrico
    @JoinColumn(name = "consumo_electrico_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    private ConsumoElectrico idConsumoElectrico;


    public int getIdConsumo() {
        return idConsumo;
    }

    public void setIdConsumo(int idConsumo) {
        this.idConsumo = idConsumo;
    }

    public ConsumoNormal getIdConsumoNormal() {
        return idConsumoNormal;
    }

    public void setIdConsumoNormal(ConsumoNormal idConsumoNormal) {
        this.idConsumoNormal = idConsumoNormal;
    }

    public ConsumoAlternativo getIdConsumoAlternativo() {
        return idConsumoAlternativo;
    }

    public void setIdConsumoAlternativo(ConsumoAlternativo idConsumoAlternativo) {
        this.idConsumoAlternativo = idConsumoAlternativo;
    }

    public ConsumoElectrico getIdConsumoElectrico() {
        return idConsumoElectrico;
    }

    public void setIdConsumoElectrico(ConsumoElectrico idConsumoElectrico) {
        this.idConsumoElectrico = idConsumoElectrico;
    }
}
