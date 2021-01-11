package com.kevingomez.FYCBackEnd.models.filters;

public class EmisionesFiltro {
    private static final String titulo = "emisiones";
    private int emisiones;
    private String tipoEmisiones;

    public static String getTitulo() {
        return titulo;
    }

    public int getEmisiones() {
        return emisiones;
    }

    public void setEmisiones(int emisiones) {
        this.emisiones = emisiones;
    }

    public String getTipoEmisiones() {
        return tipoEmisiones;
    }

    public void setTipoEmisiones(String tipoEmisiones) {
        this.tipoEmisiones = tipoEmisiones;
    }
}
