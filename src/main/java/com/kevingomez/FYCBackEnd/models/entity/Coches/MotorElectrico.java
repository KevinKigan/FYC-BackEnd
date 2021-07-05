package com.kevingomez.FYCBackEnd.models.entity.Coches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "motores_electricos")
public class MotorElectrico implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "motor_electrico_id")
    private int idMotorElectrico;

    @Column(name = "t_carga220v")
    @NotNull(message = "El tiempo de carga no puede estar vacio")
    private double tCarga220v;

    /* Con JoinTable se crea una tabla intermedia entre motores electricos y potencias electricas
     * para que un motor electrico pueda tener varias potencias electricas con el mismo identificador
     * unico del motor y los mismos identificadores unicos de pontencias. Aplica el mismo ejemplo
     * para los hp (horsepower, caballos de potencia en ingles)
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value={"idPotenciaElectrica","hibernateLazyInitializer","handler"}, allowSetters = true)
    @JoinTable(name = "motores_electricos_potencias_electrica", joinColumns=@JoinColumn(name = "motor_electrico_id"),
            inverseJoinColumns = @JoinColumn(name = "potencia_electrica_id"))
    private List<PotenciaElectrica> potenciasElectricas;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value={"idHP","hibernateLazyInitializer","handler"}, allowSetters = true)
    @JoinTable(name = "motores_electricos_hp", joinColumns=@JoinColumn(name = "motor_electrico_id"),
            inverseJoinColumns = @JoinColumn(name = "hp_id"))
    private List<HP_Electrico> hps;


    public int getIdMotorElectrico() {
        return idMotorElectrico;
    }

    public void setIdMotorElectrico(int idMotorElectrico) {
        this.idMotorElectrico = idMotorElectrico;
    }

    public double gettCarga220v() {
        return tCarga220v;
    }

    public void settCarga220v(double tCarga220v) {
        this.tCarga220v = tCarga220v;
    }

    public List<PotenciaElectrica> getPotenciasElectricas() {
        return potenciasElectricas;
    }

    public void setPotenciasElectricas(List<PotenciaElectrica> potenciasElectricas) {
        this.potenciasElectricas = potenciasElectricas;
    }

    public List<HP_Electrico> getHps() {
        return hps;
    }

    public void setHps(List<HP_Electrico> hps) {
        this.hps = hps;
    }
}
