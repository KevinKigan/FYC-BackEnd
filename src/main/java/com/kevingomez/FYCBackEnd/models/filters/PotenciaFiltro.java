package com.kevingomez.FYCBackEnd.models.filters;

public class PotenciaFiltro {
    private static final String titulo = "potencia";
    private int potenciaMin;
    private int potenciaMax;


    public static String getTitulo() {
        return titulo;
    }

    public int getPotenciaMin() {
        return potenciaMin;
    }

    public void setPotenciaMin(int potenciaMin) {
        this.potenciaMin = potenciaMin;
    }

    public int getPotenciaMax() {
        return potenciaMax;
    }

    public void setPotenciaMax(int potenciaMax) {
        this.potenciaMax = potenciaMax;
    }
}
