package com.kevingomez.FYCBackEnd.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "coches")
public class Coche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coche_id")
    private int idCoche;

    @NotEmpty(message = "El campo no puede estar vacio")
    @Size(min = 3, max = 3)
    @Column(name = "eje_motriz",nullable = false)
    private String ejeMotriz;

    // Fetch tipo lazy para que no realice la consulta y obtenga los hijos y/o nietos
    // Json ignore omite una llamada recursiva a si mismo cuando es una relacion entre entidades
    @ManyToOne(fetch = FetchType.LAZY) // Muchos coches pueden tener una marca
    @JoinColumn(name = "marca_id")
    @NotNull(message = "La marca no puede estar vacia")
    @JsonIgnoreProperties(value={"marca","hibernateLazyInitializer","handler"}, allowSetters = true) // Esto se hace por el fecth lazy
    private Marca marca;

    private String modelo;

    private String transmision;

    @ManyToOne(fetch = FetchType.LAZY) // Muchos coches pueden tener una carroceria
    @JoinColumn(name = "carroceria_id")
    @NotNull(message = "La carroceria no puede estar vacia")
    @JsonIgnoreProperties(value={"marca","hibernateLazyInitializer","handler"}, allowSetters = true) // Esto se hace por el fecth lazy
    private Carroceria carroceria;

    private int caryear;

    private int precio;

    public int getIdCoche() {
        return idCoche;
    }

    public void setIdCoche(int idCoche) {
        this.idCoche = idCoche;
    }

    public String getEjeMotriz() {
        return ejeMotriz;
    }

    public void setEjeMotriz(String ejeMotriz) {
        this.ejeMotriz = ejeMotriz;
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

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public int getCaryear() {
        return caryear;
    }

    public void setCaryear(int caryear) {
        this.caryear = caryear;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}