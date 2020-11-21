package com.kevingomez.FYCBackEnd.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "motores_electricos")
public class MotorElectrico {
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
    private List<Integer> motorElectrico;

    @ElementCollection
    private List<Integer> hp;
}
