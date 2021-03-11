package com.kevingomez.FYCBackEnd.models.entity.Usuarios;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Roles")
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rol_name",unique = true, length = 20)
    private String rolName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }
}
