package com.kevingomez.FYCBackEnd.models.entity.Usuarios;

public class Verificacion {
    private int id;
    private String codigo;
    Long time;

    public Verificacion(int id, String codigo){
        this.id = id;
        this.codigo = codigo;
        this.time = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
