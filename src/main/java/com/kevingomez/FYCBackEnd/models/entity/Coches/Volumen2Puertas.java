package com.kevingomez.FYCBackEnd.models.entity.Coches;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "volumenes_2puertas")
public class Volumen2Puertas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volumen_2puertas_id")
    private int idVolumen2Puertas;

    @NotNull(message = "El volumen del maletero no puede estar vacio")
    @Column(name = "volumen_maletero")
    private double volumenMaletero;

    @NotNull(message = "El volumen del habitaculo no puede estar vacio")
    @Column(name = "volumen_habitaculo")
    private double volumenHabitaculo;

    public int getIdVolumen2Puertas() {
        return idVolumen2Puertas;
    }

    public void setIdVolumen2Puertas(int idVolumen2Puertas) {
        this.idVolumen2Puertas = idVolumen2Puertas;
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
