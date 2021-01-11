package com.kevingomez.FYCBackEnd.models.filters;

public class MotorFiltro {
    private static final String titulo = "motor";
    private double cilindrada;
    private int cilindros;
    private String sobrealimentacion;

    public static String getTitulo() {
        return titulo;
    }

    public double getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(double cilindrada) {
        this.cilindrada = cilindrada;
    }

    public int getCilindros() {
        return cilindros;
    }

    public void setCilindros(int cilindros) {
        this.cilindros = cilindros;
    }

    public String getSobrealimentacion() {
        return sobrealimentacion;
    }

    public void setSobrealimentacion(String sobrealimentacion) {
        this.sobrealimentacion = sobrealimentacion;
    }
}
