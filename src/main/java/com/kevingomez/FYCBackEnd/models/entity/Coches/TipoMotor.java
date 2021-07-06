package com.kevingomez.FYCBackEnd.models.entity.Coches;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motor_combustion_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    private MotorCombustion motorCombustion;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motor_electrico_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    private MotorElectrico motorElectrico;


    public int getIdTipoMotor() {
        return idTipoMotor;
    }

    public void setIdTipoMotor(int idTipoMotor) {
        this.idTipoMotor = idTipoMotor;
    }

    public MotorCombustion getMotorCombustion() {
        return motorCombustion;
    }

    public void setMotorCombustion(MotorCombustion motorCombustion) {
        this.motorCombustion = motorCombustion;
    }

    public MotorElectrico getMotorElectrico() {
        return motorElectrico;
    }

    public void setMotorElectrico(MotorElectrico motorElectrico) {
        this.motorElectrico = motorElectrico;
    }
}
