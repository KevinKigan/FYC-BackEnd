package com.kevingomez.FYCBackEnd.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sobrealimentaciones")
public class Sobrealimentacion  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sobrealimentacion_id")
    private int idSobrealimentacion;

    private boolean turbo;

    private boolean supercargador;
}
