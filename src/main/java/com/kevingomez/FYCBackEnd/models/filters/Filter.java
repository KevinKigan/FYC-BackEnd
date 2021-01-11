package com.kevingomez.FYCBackEnd.models.filters;


public class Filter {
    private PrecioFiltro precio;
    private CarroceriaFiltro carroceria;
    private MotorFiltro motor;
    private ConsumoFiltro consumo;
    private PotenciaFiltro potencia;
    private EmisionesFiltro emisiones;

    public PrecioFiltro getPrecio() {
        return precio;
    }

    public void setPrecio(PrecioFiltro precio) {
        this.precio = precio;
    }

    public CarroceriaFiltro getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(CarroceriaFiltro carroceria) {
        this.carroceria = carroceria;
    }

    public MotorFiltro getMotor() {
        return motor;
    }

    public void setMotor(MotorFiltro motor) {
        this.motor = motor;
    }

    public ConsumoFiltro getConsumo() {
        return consumo;
    }

    public void setConsumo(ConsumoFiltro consumo) {
        this.consumo = consumo;
    }

    public PotenciaFiltro getPotencia() {
        return potencia;
    }

    public void setPotencia(PotenciaFiltro potencia) {
        this.potencia = potencia;
    }

    public EmisionesFiltro getEmisiones() {
        return emisiones;
    }

    public void setEmisiones(EmisionesFiltro emisiones) {
        this.emisiones = emisiones;
    }
}
