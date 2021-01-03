package com.kevingomez.FYCBackEnd.models.filters;

public class CarroceriaFiltro {
    private static final String titulo = "carroceria";
    private String carroceria;

    public static String getTitulo() {
        return titulo;
    }

    public String getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria = carroceria;
    }
}
