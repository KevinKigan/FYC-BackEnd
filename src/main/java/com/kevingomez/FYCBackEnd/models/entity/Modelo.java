package com.kevingomez.FYCBackEnd.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "modelos")
public class Modelo  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modelo_id")
    private int idModelo;

    @ManyToOne(fetch = FetchType.LAZY) // Muchos modelos pueden tener una marca
    @JoinColumn(name = "marca_id")
    @NotNull(message = "La marca no puede estar vacia")
    @JsonIgnoreProperties(value={"modelo","hibernateLazyInitializer","handler"}, allowSetters = true)
    private Marca marca;

    @NotNull(message = "El modelo no puede estar vacio")
    private String modelo;

    @NotNull(message = "La imagen no puede estar vacia")
    private String imagen;

    @OneToOne(fetch = FetchType.LAZY) // Un coche tiene un volumen
    @JoinColumn(name = "volumen_id")
    @JsonIgnoreProperties(value={"volumen2p","volumen4p","volumenHatchback","startstop","hibernateLazyInitializer","handler"}, allowSetters = true)
    private Volumen volumen;


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

    public Volumen getVolumen() {
        return volumen;
    }

    public void setVolumen(Volumen volumen) {
        this.volumen = volumen;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
