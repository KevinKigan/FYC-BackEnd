package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "carrocerias")
public class Carroceria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carroceria_id")
    private int idCarroceria;

    private String carroceria;

    public int getIdCarroceria() {
        return idCarroceria;
    }

    public void setIdCarroceria(int idCarroceria) {
        this.idCarroceria = idCarroceria;
    }

    public String getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria = carroceria;
    }
}
