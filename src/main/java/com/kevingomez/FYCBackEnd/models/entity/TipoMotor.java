package com.kevingomez.FYCBackEnd.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "tipos_motores")
public class TipoMotor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_motor_id")
    private int idTipoMotor;

    @Column(name = "motor_combustion")
    private boolean motorCombustion;


    @Column(name = "motor_electrico")
    private boolean motorElectrico;


    public int getIdTipoMotor() {
        return idTipoMotor;
    }

    public void setIdTipoMotor(int idTipoMotor) {
        this.idTipoMotor = idTipoMotor;
    }

    public boolean isMotorCombustion() {
        return motorCombustion;
    }

    public void setMotorCombustion(boolean motorCombustion) {
        this.motorCombustion = motorCombustion;
    }

    public boolean isMotorElectrico() {
        return motorElectrico;
    }

    public void setMotorElectrico(boolean motorElectrico) {
        this.motorElectrico = motorElectrico;
    }
}
