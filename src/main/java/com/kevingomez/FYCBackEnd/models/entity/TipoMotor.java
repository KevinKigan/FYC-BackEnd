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

//    @OneToOne(fetch = FetchType.LAZY)
//    @NotNull(message = "El campo 'Tipo de motor combustion' no puede estar vacio")
//    @Column(name = "motor_combustion_id")
//    private MotorConbustion motorCombustion;
//
//
//    @OneToOne(fetch = FetchType.LAZY)
//    //@NotNull(message = "El campo 'Tipo de motor electrico' no puede estar vacio")
//    @Column(name = "motor_electrico_id")
//    private MotorElectrico motorElectrico;


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
