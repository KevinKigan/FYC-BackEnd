package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IFiltrosService;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.ICarroceriaDAO;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.ICocheDAO;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IMarcaDAO;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IModeloDAO;
import com.kevingomez.FYCBackEnd.models.entity.Carroceria;
import com.kevingomez.FYCBackEnd.models.entity.Coche;
import com.kevingomez.FYCBackEnd.models.entity.Modelo;
import com.kevingomez.FYCBackEnd.models.filters.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@Service
public class FiltrosService implements IFiltrosService {

    @Autowired
    private ICocheDAO cocheDAO;
    @Autowired
    private IModeloDAO modeloDAO;
    @Autowired
    private IMarcaDAO marcaDAO;
    @Autowired
    private ICarroceriaDAO carroceriaDAO;

    private List<Modelo> modelosList = new ArrayList<>();


    /**
     * Metodo para generar la estructrura de filtros a buscar
     *
     * @param mat Matcher con los filtros
     * @return
     */
    @Override
    public Filter estructurarFiltros(Matcher mat) {
        Filter filter = new Filter();
        while (mat.find()) {
            String estructura = mat.group(1);
            String partes[] = estructura.split(", ");
            switch (partes[0].split("=")[1]) { //Obtenemos el titulo
                case "precio":
                    setPrecio(partes, filter);
                    break;
                case "carroceria":
                    setCarroceria(partes, filter);
                    break;
                case "motor":
                    setMotor(partes, filter);
                    break;
                case "consumo":
                    setConsumo(partes, filter);
                    break;
                case "emisiones":
                    setEmisiones(partes, filter);
                    break;
                case "potencia":
                    setPotencia(partes, filter);
                    break;
            }
        }
        return filter;
    }

    private void setPotencia(String[] partes, Filter filter) {
        PotenciaFiltro potenciaFiltro = new PotenciaFiltro();
        for (String parte : partes) {
            String parametro = parte.split("=")[0];
            String valor = parte.split("=")[1];
            if (parametro.equals("potencia")) {
                potenciaFiltro.setPotencia(Integer.parseInt(valor));
            }
        }
        filter.setPotencia(potenciaFiltro);
    }

    private void setEmisiones(String[] partes, Filter filter) {
        EmisionesFiltro emisionesFiltro = new EmisionesFiltro();
        for (String parte : partes) {
            String parametro = parte.split("=")[0];
            String valor = parte.split("=")[1];
            if (parametro.equals("emisiones")) {
                emisionesFiltro.setEmisiones(Integer.parseInt(valor));
            }
        }
        filter.setEmisiones(emisionesFiltro);
    }

    private void setConsumo(String[] partes, Filter filter) {
        ConsumoFiltro consumoFiltro = new ConsumoFiltro();
        for (String parte : partes) {
            String parametro = parte.split("=")[0];
            String valor = parte.split("=")[1];
            switch (parametro) {
                case "autopista":
                    consumoFiltro.setAutopista(Double.parseDouble(valor));
                    break;
                case "mixto":
                    consumoFiltro.setMixto(Double.parseDouble(valor));
                    break;
                case "ciudad":
                    consumoFiltro.setCiudad(Double.parseDouble(valor));
                    break;
            }
            filter.setConsumo(consumoFiltro);
        }
    }

    private void setCarroceria(String[] partes, Filter filter) {
        CarroceriaFiltro carroceriaFiltro = new CarroceriaFiltro();
        for (String parte : partes) {
            String parametro = parte.split("=")[0];
            String valor = parte.split("=")[1];
            if (parametro.equals("value")) {
                carroceriaFiltro.setCarroceria(valor);
            }
        }
        filter.setCarroceria(carroceriaFiltro);
    }

    private void setMotor(String[] partes, Filter filter) {
        MotorFiltro motorFiltro = new MotorFiltro();
        for (String parte : partes) {
            String parametro = parte.split("=")[0];
            String valor = parte.split("=")[1];
            switch (parametro) {
                case "cilindrada":
                    motorFiltro.setCilindrada(valor);
                    break;
                case "cilindros":
                    motorFiltro.setCilindros(Integer.parseInt(valor));
                    break;
                case "sobrealimentacion":
                    motorFiltro.setSobrealimentacion(valor);
                    break;
                case "combustible":
                    motorFiltro.setCombustible(valor);
                    break;
                case "emisiones":
                    motorFiltro.setEmisiones(valor);
                    break;
            }
        }
        filter.setMotor(motorFiltro);
    }

    private void setPrecio(String[] partes, Filter filter) {
        PrecioFiltro precioFiltro = new PrecioFiltro();
        for (String parte : partes) {
            String parametro = parte.split("=")[0];
            String valor = parte.split("=")[1];
            if (parametro.equals("minimo")) {
                precioFiltro.setMinimo(Integer.parseInt((valor)));
            } else if (parametro.equals("maximo")) {
                precioFiltro.setMaximo(Integer.parseInt((valor)));
            }
        }
        filter.setPrecio(precioFiltro);
    }

    @Override
    public List<Modelo> filtrarModelos(Filter filtros) {
        modelosList = new ArrayList<>();
        if (filtros.getPrecio() != null) {
            filtrarPrecio(filtros.getPrecio());
        }
        if (filtros.getCarroceria() != null) {
            filtrarCarroceria(filtros.getCarroceria());
        }
        if (filtros.getConsumo() != null) {
            filtrarConsumo(filtros.getConsumo());
        }
        if (filtros.getEmisiones() != null) {
            filtrarEmisiones(filtros.getEmisiones());
        }
        if (filtros.getMotor() != null) {
            filtrarMotor(filtros.getMotor());
        }
        if (filtros.getPotencia() != null) {
            filtrarPotencia(filtros.getPotencia());
        }
        return modelosList;
    }

    private void filtrarPotencia(PotenciaFiltro potencia) {

    }

    private void filtrarMotor(MotorFiltro motor) {

    }

    private void filtrarEmisiones(EmisionesFiltro emisiones) {

    }

    private void filtrarConsumo(ConsumoFiltro consumo) {

    }

    private void filtrarPrecio(PrecioFiltro precio) {

    }

    private void filtrarCarroceria(CarroceriaFiltro carroceria) {
        Carroceria c = this.carroceriaDAO.getCarroceriaByCarroceria(carroceria.getCarroceria());
        List<Coche> coches = this.cocheDAO.findAllModeloDistinctByCarroceria_IdCarroceria(c.getIdCarroceria());
        ArrayList<Integer> modelos = new ArrayList<>();
        for (Coche coche : coches) {
            if (!modelos.contains(coche.getModelo().getIdModelo()))  {
                modelos.add(coche.getModelo().getIdModelo());
            }
        }

        List<Modelo> mod = this.modeloDAO.findAllByIdModeloIn(modelos);
        modelosList = reinicializarModelos(mod);
    }

    /**
     * Metodo para desbuguear la lista de modelos
     *
     * @param mod
     * @return
     */
    private List<Modelo> reinicializarModelos(List<Modelo> mod) {
        List<Modelo> modelosReturn = new ArrayList<>();
        for (Modelo modelo : mod) {
            Modelo modAux = new Modelo();
            modAux.setIdModelo(modelo.getIdModelo());
            modAux.setMarca(modelo.getMarca());
            modAux.setModelo(modelo.getModelo());
            modAux.setVolumen(modelo.getVolumen());
            modelosReturn.add(modAux);
        }
        return modelosReturn;
    }
}

//[
//        {
//        "title": "precio",
//        "minimo": "1",
//        "maximo": "2500"
//        },
//        {
//        "title": "carroceria",
//        "value": "cabrio"
//        },
//        {
//        "title": "potencia",
//        "potencia": "300"
//        },
//        {
//        "title": "emisiones",
//        "emisiones": "300"
//        },
//        {
//        "title": "motor",
//        "cilindrada": "2",
//        "cilindros": "4",
//        "combustible": "Gasolina 95",
//        "emisiones": "Euro 6",
//        "sobrealimentacion": "Turbo"
//        },
//        {
//        "title": "consumo",
//        "ciudad": "7.2",
//        "autopista": "6.5",
//        "mixto": "7.0"
//        }
//        ]
