package com.kevingomez.FYCBackEnd.models.filters;

public class PrecioFiltro {
    private static final String titulo = "precio";
    private int minimo;
    private int maximo;

    public static String getTitulo() {
        return titulo;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }
}
