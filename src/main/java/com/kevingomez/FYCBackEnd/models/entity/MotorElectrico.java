package com.kevingomez.FYCBackEnd.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
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
    private double tCarga220v;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    @JoinTable(name = "motores_electricos_potencias_electrica", joinColumns=@JoinColumn(name = "motor_electrico_id"), inverseJoinColumns = @JoinColumn(name = "potencia_electrica_id"))
    private List<PotenciaElectrica> potenciaElectrica;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "motores_electricos_hp", joinColumns=@JoinColumn(name = "motor_electrico_id"), inverseJoinColumns = @JoinColumn(name = "hp_id"))
    private List<HP_Electrico> hp;

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

    public List<PotenciaElectrica> getPotenciaElectrica() {
        return potenciaElectrica;
    }

    public void setPotenciaElectrica(List<PotenciaElectrica> potenciaElectrica) {
        this.potenciaElectrica = potenciaElectrica;
    }

    public List<HP_Electrico> getHp() {
        return hp;
    }

    public void setHp(List<HP_Electrico> hp) {
        this.hp = hp;
    }
}
