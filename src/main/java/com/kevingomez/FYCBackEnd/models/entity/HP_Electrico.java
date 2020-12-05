package com.kevingomez.FYCBackEnd.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "hp_electricos")
public class HP_Electrico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hp_id")
    private int idHP;

    @NotNull(message = "El campo CV no puede estar vacio")
    private int hp;

    public int getIdHP() {
        return idHP;
    }

    public void setIdHP(int idHP) {
        this.idHP = idHP;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
