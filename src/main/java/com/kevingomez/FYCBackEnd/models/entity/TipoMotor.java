package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "tipos_motores")
public class TipoMotor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "motor_id")
    private int idMotor;

    @NotNull(message = "El campo 'Tipo de motor combustion' no puede estar vacio")
    @Column(name = "motor_combustion")
    private boolean motorCombustion;


    @NotNull(message = "El campo 'Tipo de motor electrico' no puede estar vacio")
    @Column(name = "motor_electrico")
    private boolean motorElectrico;

    public int getIdMotor() {
        return idMotor;
    }

    public void setIdMotor(int idMotor) {
        this.idMotor = idMotor;
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
