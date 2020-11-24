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

    @OneToOne(fetch = FetchType.LAZY) // Un motor tiene un coche
    @JoinColumn(name = "coche_id")
    @JsonIgnoreProperties(value={"coche","hibernateLazyInitializer","handler"}, allowSetters = true)
    private Coche coche;

    private double tCarga220v;

    @ElementCollection
    @JoinColumn(name = "potencia_electrica_id")
    private List<Integer> potenciaElectrica;

    @ElementCollection
    @JoinColumn(name = "hp_id")
    private List<Integer> hp;

    public int getIdMotorElectrico() {
        return idMotorElectrico;
    }

    public void setIdMotorElectrico(int idMotorElectrico) {
        this.idMotorElectrico = idMotorElectrico;
    }

    public Coche getCoche() {
        return coche;
    }

    public void setCoche(Coche coche) {
        this.coche = coche;
    }

    public double gettCarga220v() {
        return tCarga220v;
    }

    public void settCarga220v(double tCarga220v) {
        this.tCarga220v = tCarga220v;
    }

    public List<Integer> getPotenciaElectrica() {
        return potenciaElectrica;
    }

    public void setPotenciaElectrica(List<Integer> potenciaElectrica) {
        this.potenciaElectrica = potenciaElectrica;
    }

    public List<Integer> getHp() {
        return hp;
    }

    public void setHp(List<Integer> hp) {
        this.hp = hp;
    }
}
