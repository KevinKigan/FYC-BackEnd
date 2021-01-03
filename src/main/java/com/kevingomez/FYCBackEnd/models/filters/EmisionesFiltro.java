package com.kevingomez.FYCBackEnd.models.filters;

public class EmisionesFiltro {
    private static final String titulo = "emisiones";
    private int emisiones;

    public static String getTitulo() {
        return titulo;
    }

    public int getEmisiones() {
        return emisiones;
    }

    public void setEmisiones(int emisiones) {
        this.emisiones = emisiones;
    }
}
