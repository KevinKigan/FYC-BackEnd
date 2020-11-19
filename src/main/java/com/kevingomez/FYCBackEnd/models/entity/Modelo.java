package com.kevingomez.FYCBackEnd.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "modelos")
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modelo_id")
    private int idModelo;

    @ManyToOne(fetch = FetchType.LAZY) // Muchos modelos pueden tener una marca
    @JoinColumn(name = "marca_id")
    @NotNull(message = "La marca no puede estar vacia")
    @JsonIgnoreProperties(value={"modelo","hibernateLazyInitializer","handler"}, allowSetters = true) // Esto se hace por el fecth lazy
    private Marca marca;

    @NotNull(message = "La modelo no puede estar vacio")
    private String modelo;

    public int getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
