package com.kevingomez.FYCBackEnd.models.filters;

public class ConsumoFiltro {
    private static final String titulo = "consumo";
    private double mixto;

    public static String getTitulo() {
        return titulo;
    }

    public double getMixto() {
        return mixto;
    }

    public void setMixto(double mixto) {
        this.mixto = mixto;
    }

}
