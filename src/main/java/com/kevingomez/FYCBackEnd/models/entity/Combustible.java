package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "combustibles")
public class Combustible  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combustible_id")
    private int idCombustible;

    @Column(name = "combustible_normal")
    private String combustibleNormal;

    @Column(name = "combustible_alternativo")
    private String combustibleAlternativo;


}
