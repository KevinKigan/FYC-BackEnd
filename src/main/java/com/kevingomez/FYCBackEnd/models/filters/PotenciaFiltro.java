package com.kevingomez.FYCBackEnd.models.filters;

public class PotenciaFiltro {
    private static final String titulo = "potencia";
    private int potencia;

    public static String getTitulo() {
        return titulo;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }
}
