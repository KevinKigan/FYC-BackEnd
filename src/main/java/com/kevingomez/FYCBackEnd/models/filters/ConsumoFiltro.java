package com.kevingomez.FYCBackEnd.models.filters;

public class ConsumoFiltro {
    private static final String titulo = "consumo";
    private double ciudad;
    private double mixto;
    private double autopista;

    public static String getTitulo() {
        return titulo;
    }

    public double getCiudad() {
        return ciudad;
    }

    public void setCiudad(double ciudad) {
        this.ciudad = ciudad;
    }

    public double getMixto() {
        return mixto;
    }

    public void setMixto(double mixto) {
        this.mixto = mixto;
    }

    public double getAutopista() {
        return autopista;
    }

    public void setAutopista(double autopista) {
        this.autopista = autopista;
    }
}
