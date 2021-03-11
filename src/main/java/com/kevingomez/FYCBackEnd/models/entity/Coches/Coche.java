package com.kevingomez.FYCBackEnd.models.entity.Coches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "coches")
public class Coche implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coche_id")
    private int idCoche;

    @NotNull(message = "El campo Eje Motriz no puede estar vacio")
    @Size(min = 3, max = 3)
    @Column(name = "eje_motriz",nullable = false)
    private String ejeMotriz;

    // Fetch tipo lazy (carga perezosa) para que no realice la consulta de los hijos y/o nietos tambien
    // Json ignore omite los valores que se especifiquen de la entidad que tiene el objeto
    @ManyToOne(fetch = FetchType.LAZY) // Muchos coches pueden tener una marca
    @JoinColumn(name = "marca_id")
    @NotNull(message = "La marca no puede estar vacia")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true) // Esto se hace por el fecth lazy
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY) // Muchos coches pueden tener un modelo
    @JoinColumn(name = "modelo_id")
    @NotNull(message = "La modelo no puede estar vacia")
    @JsonIgnoreProperties(value={"marca","hibernateLazyInitializer","handler"}, allowSetters = true)
    private Modelo modelo;

    @NotNull(message = "La transmision no puede estar vacia")
    private String transmision;

    @ManyToOne(fetch = FetchType.LAZY) // Muchos coches pueden tener una carroceria
    @JoinColumn(name = "carroceria_id")
    @NotNull(message = "La carroceria no puede estar vacia")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    private Carroceria carroceria;

    @OneToOne(fetch = FetchType.LAZY) // Un coche tiene un tipo de motor
    @JoinColumn(name = "tipo_motor_id")
    @NotNull(message = "La tipo de motor no puede estar vacio")
    @JsonIgnoreProperties(value={"motorCombustion","motorElectrico","hibernateLazyInitializer","handler"}, allowSetters = true)
    private TipoMotor tipoMotor;

    @NotNull(message = "El campo 'caryear' no puede estar vacio")
    private int caryear;

    private int precio;

    @OneToOne(fetch = FetchType.LAZY) // Un coche tiene un consumo
    @JoinColumn(name = "consumo_id")
    @JsonIgnoreProperties(value={"idConsumoNormal","idConsumoAlternativo","idConsumoElectrico","hibernateLazyInitializer","handler"}, allowSetters = true) // Esto se hace por el fecth lazy
    private Consumo consumo;


    public Consumo getConsumo() {
        return consumo;
    }

    public void setConsumo(Consumo consumo) {
        this.consumo = consumo;
    }

    public Carroceria getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(Carroceria carroceria) {
        this.carroceria = carroceria;
    }

    public TipoMotor getTipoMotor() {
        return tipoMotor;
    }

    public void setTipoMotor(TipoMotor tipoMotor) {
        this.tipoMotor = tipoMotor;
    }

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

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
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
