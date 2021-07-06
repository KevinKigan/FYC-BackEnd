package com.kevingomez.FYCBackEnd.models.entity.Coches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "motores_combustion")
public class MotorCombustion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "motor_combustion_id")
    private int idMotorCombustion;

    private int cilindros;

    private double cilindrada;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sobrealimentacion_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    @NotNull(message = "La sobrealimentacion no puede estar vacia")
    private Sobrealimentacion sobrealimentacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combustible_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    @NotNull(message = "El combustible no puede estar vacio")
    private Combustible combustible;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "emisiones_id")
    @JsonIgnoreProperties(value={"idTipoEmisiones","hibernateLazyInitializer","handler"}, allowSetters = true)
    private Emisiones emisiones;

    @NotNull(message = "La potencia (CV) no puede estar vacia")
    private int hp;

    public Emisiones getEmisiones() {
        return emisiones;
    }

    public void setEmisiones(Emisiones emisiones) {
        this.emisiones = emisiones;
    }

    public int getIdMotorCombustion() {
        return idMotorCombustion;
    }

    public void setIdMotorCombustion(int idMotorCombustion) {
        this.idMotorCombustion = idMotorCombustion;
    }

    public int getCilindros() {
        return cilindros;
    }

    public void setCilindros(int cilindros) {
        this.cilindros = cilindros;
    }

    public double getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(double cilindrada) {
        this.cilindrada = cilindrada;
    }

    public Sobrealimentacion getSobrealimentacion() {
        return sobrealimentacion;
    }

    public void setSobrealimentacion(Sobrealimentacion sobrealimentacion) { this.sobrealimentacion = sobrealimentacion; }

    public Combustible getCombustible() {
        return combustible;
    }

    public void setCombustible(Combustible combustible) {
        this.combustible = combustible;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

}
