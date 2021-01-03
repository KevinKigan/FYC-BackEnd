package com.kevingomez.FYCBackEnd.models.filters;

public class MotorFiltro {
    private static final String titulo = "motor";
    private String cilindrada;
    private int cilindros;
    private String sobrealimentacion;
    private String combustible;
    private String emisiones;

    public static String getTitulo() {
        return titulo;
    }

    public String getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(String cilindrada) {
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

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public String getEmisiones() {
        return emisiones;
    }

    public void setEmisiones(String emisiones) {
        this.emisiones = emisiones;
    }
}
