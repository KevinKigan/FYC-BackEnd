package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "volumenes_hatchback")
public class VolumenHatchback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volumen_hatchback_id")
    private int idVolumenHatchback;

    @NotNull(message = "El volumen del maletero no puede estar vacio")
    @Column(name = "volumen_maletero")
    private double volumenMaletero;

    @NotNull(message = "El volumen del habitaculo no puede estar vacio")
    @Column(name = "volumen_habitaculo")
    private double volumenHabitaculo;


    public int getIdVolumenHatchback() {
        return idVolumenHatchback;
    }

    public void setIdVolumenHatchback(int idVolumenHatchback) {
        this.idVolumenHatchback = idVolumenHatchback;
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
