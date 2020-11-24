package com.kevingomez.FYCBackEnd.models.entity;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "caracteristicas")
public class Caracteristica  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "El campo 'Hatchback' no puede estar vacio")
    @Column(name = "cocheHatchback", nullable = false)
    private boolean maleteroHatchback;

    @NotNull(message = "El campo 'Coche de dos puertas' no puede estar vacio")
    @Column(name = "coche2puertas", nullable = false)
    private boolean maletero2puertas;

    @NotNull(message = "El campo 'Coche de cuatro puertas' no puede estar vacio")
    @Column(name = "coche4puertas", nullable = false)
    private boolean maletero4puertas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMaleteroHatchback() {
        return maleteroHatchback;
    }

    public void setMaleteroHatchback(boolean maleteroHatchback) {
        this.maleteroHatchback = maleteroHatchback;
    }

    public boolean isMaletero2puertas() {
        return maletero2puertas;
    }

    public void setMaletero2puertas(boolean maletero2puertas) {
        this.maletero2puertas = maletero2puertas;
    }

    public boolean isMaletero4puertas() {
        return maletero4puertas;
    }

    public void setMaletero4puertas(boolean maletero4puertas) {
        this.maletero4puertas = maletero4puertas;
    }
}
