package com.kevingomez.FYCBackEnd.models.entity.Coches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "volumenes")
public class Volumen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volumen_id")
    private int idVolumen;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Un coche tiene un volumen 2 puertas
    @JoinColumn(name = "volumen_2puertas_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    private Volumen2Puertas volumen2p;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Un coche tiene un volumen 4 puertas
    @JoinColumn(name = "volumen_4puertas_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    private Volumen4Puertas volumen4p;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Un coche tiene un volumen hatchback
    @JoinColumn(name = "volumen_hatchback_id")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
    private VolumenHatchback volumenHatchback;

    @NotNull(message = "El campo StartStop no puede estar vacio")
    private String startstop;


    public int getIdVolumen() {
        return idVolumen;
    }

    public void setIdVolumen(int idVolumen) {
        this.idVolumen = idVolumen;
    }

    public Volumen2Puertas getVolumen2p() {
        return volumen2p;
    }

    public void setVolumen2p(Volumen2Puertas volumen2p) {
        this.volumen2p = volumen2p;
    }

    public Volumen4Puertas getVolumen4p() {
        return volumen4p;
    }

    public void setVolumen4p(Volumen4Puertas volumen4p) {
        this.volumen4p = volumen4p;
    }

    public VolumenHatchback getVolumenHatchback() {
        return volumenHatchback;
    }

    public void setVolumenHatchback(VolumenHatchback volumenHatchback) {
        this.volumenHatchback = volumenHatchback;
    }

    public String getStartstop() {
        return startstop;
    }

    public void setStartstop(String startstop) {
        this.startstop = startstop;
    }
}
