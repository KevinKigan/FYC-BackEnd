package com.kevingomez.FYCBackEnd.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "marcas")
public class Marca  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marca_id")
    private int idMarca;

    @NotNull(message = "El campo Marca no puede estar vacio")
    @Column(name = "marca_coche")
    private String marcaCoche;

    @NotNull(message = "El campo url de la marca no puede estar vacio")
    private String url;

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getMarcaCoche() {
        return marcaCoche;
    }

    public void setMarcaCoche(String marcaCoche) {
        this.marcaCoche = marcaCoche;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
