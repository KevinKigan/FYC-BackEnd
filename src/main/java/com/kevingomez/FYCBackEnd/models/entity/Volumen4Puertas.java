package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "volumenes_4puertas")
public class Volumen4Puertas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volumen_4puertas_id")
    private int idVolumen4Puertas;

    @NotNull(message = "El volumen del maletero no puede estar vacio")
    @Column(name = "volumen_maletero")
    private double volumenMaletero;

    @NotNull(message = "El volumen del habitaculo no puede estar vacio")
    @Column(name = "volumen_habitaculo")
    private double volumenHabitaculo;


    public int getIdVolumen4Puertas() {
        return idVolumen4Puertas;
    }

    public void setIdVolumen4Puertas(int idVolumen4Puertas) {
        this.idVolumen4Puertas = idVolumen4Puertas;
    }

    public double getVolumenMaletero() {
        return volumenMaletero;
    }

    public void setVolumenMaletero(double volumenMaletero) {
        this.volumenMaletero = volumenMaletero;
    }

    public double getVolumenHabitaculo() {
        return volumenHabitaculo;
    }

    public void setVolumenHabitaculo(double volumenHabitaculo) {
        this.volumenHabitaculo = volumenHabitaculo;
    }
}
