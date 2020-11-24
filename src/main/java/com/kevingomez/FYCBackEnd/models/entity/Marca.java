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

    @NotNull(message = "El campo 'Marca' no puede estar vacio")
    @Column(name = "marca_coche")
    private String marcaCoche;

    // Fetch tipo lazy para que no realice la consulta y obtenga los hijos y/o nietos
    // Json ignore omite una llamada recursiva a si mismo cuando es una relacion entre entidades
//    @OneToOne(fetch = FetchType.LAZY)
//    @JsonIgnoreProperties(value={"marca","hibernateLazyInitializer","handler"}, allowSetters = true)
//    private Coche coche;

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
//
//    public Coche getCoche() {
//        return coche;
//    }
//
//    public void setCoche(Coche coche) {
//        this.coche = coche;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
