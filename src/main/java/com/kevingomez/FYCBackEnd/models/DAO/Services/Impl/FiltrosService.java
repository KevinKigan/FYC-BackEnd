package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IFiltrosService;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.*;
import com.kevingomez.FYCBackEnd.models.entity.*;
import com.kevingomez.FYCBackEnd.models.filters.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private IEmisionesDAO emisionesDAO;
    @Autowired
    private IMotoresCombustionDAO motoresCombustionDAO;
    @Autowired
    private ITipoMotorDAO tipoMotorDAO;
    @Autowired
    private ITipoEmisionesDAO tipoEmisionesDAO;
    @Autowired
    private IConsumoDAO consumoDAO;
    @Autowired
    private IConsumoNormalDAO consumoNormalDAO;
    @Autowired
    private ICarroceriaDAO carroceriaDAO;

    private List<Modelo> modelosList = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(FiltrosService.class);
    private static final double MAXIMO_CONSUMO = 10000.0;
    private static final int MAXIMO_EMISIONES = 1000000;


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

    /**
     * Metodo para settear los strings del filtro a tipo potencia
     *
     * @param partes Strings con el nombre del parametro y el valor
     * @param filter Filtros que se van a utilizar
     */
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

    /**
     * Metodo para settear los strings del filtro a tipo emisiones
     *
     * @param partes Strings con el nombre del parametro y el valor
     * @param filter Filtros que se van a utilizar
     */
    private void setEmisiones(String[] partes, Filter filter) {
        EmisionesFiltro emisionesFiltro = new EmisionesFiltro();
        for (String parte : partes) {
            String parametro = parte.split("=")[0];
            String valor = parte.split("=")[1];
            if (parametro.equals("emisiones")) {
                if (valor.equals("cualquiera")) {
                    emisionesFiltro.setEmisiones(MAXIMO_EMISIONES);
                } else {
                    emisionesFiltro.setEmisiones(Integer.parseInt(valor));
                }
            } else if (parametro.equals("tipo_emisiones")) {
                emisionesFiltro.setTipoEmisiones(valor);
            }
        }
        filter.setEmisiones(emisionesFiltro);
    }

    /**
     * Metodo para settear los strings del filtro a tipo consumo
     *
     * @param partes Strings con el nombre del parametro y el valor
     * @param filter Filtros que se van a utilizar
     */
    private void setConsumo(String[] partes, Filter filter) {
        ConsumoFiltro consumoFiltro = new ConsumoFiltro();
        for (String parte : partes) {
            String parametro = parte.split("=")[0];
            String valor = parte.split("=")[1];
            switch (parametro) {
                case "autopista":
                    if (valor.equals("Cualquiera")) {
                        consumoFiltro.setAutopista(MAXIMO_CONSUMO);
                    } else {
                        consumoFiltro.setAutopista(Double.parseDouble(valor));
                    }
                    break;
                case "mixto":
                    if (valor.equals("Cualquiera")) {
                        consumoFiltro.setMixto(MAXIMO_CONSUMO);
                    } else {
                        consumoFiltro.setMixto(Double.parseDouble(valor));
                    }
                    break;
                case "ciudad":
                    if (valor.equals("Cualquiera")) {
                        consumoFiltro.setCiudad(MAXIMO_CONSUMO);
                    } else {
                        consumoFiltro.setCiudad(Double.parseDouble(valor));
                    }
                    break;
            }
            filter.setConsumo(consumoFiltro);
        }
    }

    /**
     * Metodo para settear los strings del filtro a tipo carroceria
     *
     * @param partes Strings con el nombre del parametro y el valor
     * @param filter Filtros que se van a utilizar
     */
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

    /**
     * Metodo para settear los strings del filtro a tipo motor
     *
     * @param partes Strings con el nombre del parametro y el valor
     * @param filter Filtros que se van a utilizar
     */
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

    /**
     * Metodo para settear los strings del filtro a tipo precio
     *
     * @param partes Strings con el nombre del parametro y el valor
     * @param filter Filtros que se van a utilizar
     */
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

    /**
     * Metodo para aplicar los filtros de busqueda de los modelos
     *
     * @param pageable
     * @param filtros  Filtros a aplicar
     * @return
     */
    @Override
    public Page<Modelo> filtrarModelos(Pageable pageable, Filter filtros) {
        modelosList = new ArrayList<>();

        if (filtros.getPrecio() != null) {
            log.info("Filtramos por Precio");
            filtrarPrecio(filtros.getPrecio());
        }
        if (filtros.getCarroceria() != null) {
            log.info("Filtramos por Carroceria");
            filtrarCarroceria(filtros.getCarroceria());
        }
        if (filtros.getConsumo() != null) {
            log.info("Filtramos por Consumo");
            filtrarConsumo(filtros.getConsumo());
        }
        if (filtros.getEmisiones() != null) {
            log.info("Filtramos por Emisiones");
            filtrarEmisiones(filtros.getEmisiones());
        }
        if (filtros.getMotor() != null) {
            log.info("Filtramos por Motor");
            filtrarMotor(filtros.getMotor());
        }
        if (filtros.getPotencia() != null) {
            log.info("Filtramos por Potencia");
            filtrarPotencia(filtros.getPotencia());
        }
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        int total = modelosList.size();
        int start = (int) (pageRequest.getOffset());
        int end = Math.min((start + pageRequest.getPageSize()), total);

        List<Modelo> output = new ArrayList<>();

        if (start <= end) {
            output = modelosList.subList(start, end);
        }
        return new PageImpl<>(output, pageRequest, total);
    }

    /**
     * Metodo real de filtrado de potencia
     *
     * @param potencia
     */
    private void filtrarPotencia(PotenciaFiltro potencia) {

    }

    /**
     * Metodo real de filtrado de motor
     *
     * @param motor
     */
    private void filtrarMotor(MotorFiltro motor) {

    }

    /**
     * Metodo real de filtrado de emisiones
     *
     * @param emisiones
     */
    private void filtrarEmisiones(EmisionesFiltro emisiones) {
        List<Emisiones> emisionesList = this.emisionesDAO.findAllByCO2IsLessThanEqual(emisiones.getEmisiones());
        List<Emisiones> tipoEmisionesList = new ArrayList<>();
        if(!emisiones.getTipoEmisiones().equals("cualquiera")) tipoEmisionesList = this.emisionesDAO.
                findAllByTipoEmisones_IdTipoEmisiones(
                  this.tipoEmisionesDAO.findByTipoEmisiones(emisiones.getTipoEmisiones())
                  .getIdTipoEmisiones()
                );
        modelosList = reinicializarModelos(eliminarDuplicados(
            this.cocheDAO.findAllByTipoMotorIn(                                 // Buscamos los coches que tengas los tipos de motores
                this.tipoMotorDAO.findAllByMotorCombustionIn(                   // Buscamos los tipos de motores que tengan los motores
                    this.motoresCombustionDAO.findAllByEmisiones_IdEmisionesIn( // Buscamos los motores que coincidan con las emisiones
                         buscarCoincidencias(emisionesList, tipoEmisionesList)
                    )
                )
            )
        ));
    }



    /**
     * Metodo real de filtrado de consumo
     *
     * @param consumo
     */
    private void filtrarConsumo(ConsumoFiltro consumo) {
        List<Consumo> consAuto = this.consumoDAO.findAllByIdConsumoNormalIn(this.consumoNormalDAO.findAllByAutopistaIsLessThanEqual(consumo.getAutopista()));
        List<Consumo> consComb = this.consumoDAO.findAllByIdConsumoNormalIn(this.consumoNormalDAO.findAllByCombinadoIsLessThanEqual((consumo.getMixto())));
        List<Consumo> consCiud = this.consumoDAO.findAllByIdConsumoNormalIn(this.consumoNormalDAO.findAllByCiudadIsLessThanEqual((consumo.getCiudad())));
        ArrayList<List<Consumo>> listArrayList = new ArrayList<>();
        listArrayList.add(consAuto);
        listArrayList.add(consCiud);
        listArrayList.add(consComb);
        ArrayList<Integer> listaConincidencias = buscarCoincidencias(listArrayList);
        modelosList = reinicializarModelos(eliminarDuplicados(this.cocheDAO.findAllByConsumo_IdConsumoIn(listaConincidencias)));
    }


    /**
     * Metodo real de filtrado de precio
     *
     * @param precio
     */
    private void filtrarPrecio(PrecioFiltro precio) {
        List<Coche> coches = this.cocheDAO.findAllByPrecioIsGreaterThanEqualAndPrecioIsLessThanEqual(precio.getMinimo(), precio.getMaximo());
        modelosList = reinicializarModelos(eliminarDuplicados(coches));
    }


    /**
     * Metodo real de filtrado de carroceria
     *
     * @param carroceria
     */
    private void filtrarCarroceria(CarroceriaFiltro carroceria) {
        Carroceria c = this.carroceriaDAO.getCarroceriaByCarroceria(carroceria.getCarroceria());
        List<Coche> coches = this.cocheDAO.findAllModeloDistinctByCarroceria_IdCarroceria(c.getIdCarroceria());
        modelosList = reinicializarModelos(eliminarDuplicados(coches));
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

    /**
     * Metodo para eliminar los coches duplicados
     * y retornar todos los modelos que coincidan
     *
     * @param coches Lista con los coches
     * @return Lista de los modelos sin duplicar
     */
    private List<Modelo> eliminarDuplicados(List<Coche> coches) {
        ArrayList<Integer> modelos = new ArrayList<>();
        ArrayList<Integer> modelosAux = new ArrayList<>();
        for (Modelo modelo : modelosList) {
            modelosAux.add(modelo.getIdModelo());
        }
        for (Coche coche : coches) {
            if (!modelos.contains(coche.getModelo().getIdModelo())) {
                if (modelosAux.isEmpty()) {
                    modelos.add(coche.getModelo().getIdModelo());
                } else if (modelosAux.contains(coche.getModelo().getIdModelo())) {
                    modelos.add(coche.getModelo().getIdModelo());
                }
            }
        }
        return this.modeloDAO.findAllByIdModeloIn(modelos);
    }

    /**
     * Metodo para buscar las coincidencias de las emisiones en
     * los registros de CO2 y tipo de emisiones
     *
     * @param emisionesList
     * @param tipoEmisionesList
     * @return Array con los ids de los consumos que coinciden
     */
    private ArrayList<Integer> buscarCoincidencias(List<Emisiones> emisionesList, List<Emisiones> tipoEmisionesList) {
        ArrayList<Integer> listaIdsConicidentes = new ArrayList<>();
        List<Emisiones> listaSmall;
        List<Emisiones> listaBig;
        if(emisionesList.size()<=tipoEmisionesList.size()){
            listaSmall = emisionesList;
            listaBig = tipoEmisionesList;
        }else{
            listaSmall = tipoEmisionesList;
            listaBig = emisionesList;
        }
        for (Emisiones emision: listaSmall) {
            if(listaBig.contains(emision)){
                listaIdsConicidentes.add(emision.getIdEmisiones());
            }
        }
        return listaIdsConicidentes;
    }

    /**
     * Metodo para buscar las coincidencias de los consumos en
     * los registros de autopista, ciudad y combinado
     *
     * @param listArrayList Array con las listas de consumos
     * @return Array con los ids de los consumos que coinciden
     */
    private ArrayList<Integer> buscarCoincidencias(ArrayList<List<Consumo>> listArrayList) {
        ArrayList<Integer> listaIdsConicidentes = new ArrayList<>();
        if (listArrayList.size() == 1) {
            List<Consumo> listCons = listArrayList.get(0);
            for (Consumo cons : listCons) listaIdsConicidentes.add(cons.getIdConsumo());
        } else {
            for (int i = 0; i < listArrayList.size() - 1; i++) {
                List<Consumo> list1 = listArrayList.get(i);
                List<Consumo> list2 = listArrayList.get(i + 1);

                ArrayList<Integer> ids0 = obtenerIdsConsumo(list1);
                ArrayList<Integer> ids1 = obtenerIdsConsumo(list2);
                if (listaIdsConicidentes.isEmpty()) {
                    for (int id : ids1) if (ids0.contains(id)) listaIdsConicidentes.add(id);
                } else {
                    ArrayList<Integer> posibles = new ArrayList<>(listaIdsConicidentes);
                    for (int id : listaIdsConicidentes) if (!ids1.contains(id)) posibles.remove((Object) id);
                    listaIdsConicidentes = posibles;
                }
            }
        }
        return listaIdsConicidentes;
    }

    /**
     * Metodo para obtener los ids de consumo y
     * añadirlos a un array
     *
     * @param consumo
     * @return
     */
    private ArrayList<Integer> obtenerIdsConsumo(List<Consumo> consumo) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Consumo cons : consumo) {
            ids.add(cons.getIdConsumo());
        }
        return ids;
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
//        "emisiones": "6   00",
//        "tipo_emisiones": "Euro 6"
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


//[
//        {
//        "title": "precio",
//        "minimo": "20000",
//        "maximo": "150000"
//        },
//        {
//        "title": "carroceria",
//        "value": "Suv"
//        },
//        {
//        "title": "consumo",
//        "ciudad": "9",
//        "autopista": "9",
//        "mixto": "10"
//        },
//        {
//        "title": "emisiones",
//        "emisiones": "400",
//        "tipo_emisiones": "Euro 6"
//        }
//]
