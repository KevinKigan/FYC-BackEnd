package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.*;
import com.kevingomez.FYCBackEnd.models.entity.Coches.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Math.*;

@Service
public class CocheService implements ICocheService {
    private static final double MARGEN = 0.15;
    private static final double MIN = 0;
    private static final double MAX = pow(10,10);
    @Autowired
    private ICocheDAO cocheDAO;
    @Autowired
    private IModeloDAO modeloDAO;
    @Autowired
    private IMarcaDAO marcaDAO;
    @Autowired
    private ICarroceriaDAO carroceriaDAO;
    @Autowired
    private IVolumenDAO volumenDAO;
    @Autowired
    private IConsumoDAO consumoDAO;
    @Autowired
    private ITipoMotorDAO tipoMotorDAO;
    @Autowired
    private IEmisionesDAO emisionesDAO;
    @Autowired
    private IConsumoNormalDAO consumoNormalDAO;
    @Autowired
    private IMotorCombustionDAO motorCombustionDAO;
    @Autowired
    private IMotorElectricoDAO motorElectricoDAO;
    @Autowired
    private FiltrosService filtroService;

    private static final Logger log = LoggerFactory.getLogger(CocheService.class);

    /**
     * Metodo para retornar todos los coches
     *
     * @return Lista de coches
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public HashMap<String, String> findAllPreciosList(List<Integer> ids) {
        //TODO buscar coches que tienen esos id de modelo y coger el que tenga menos precio. Retronar los precios
        List<Coche> coches = cocheDAO.findPrecio(ids);
        HashMap<String, String> hash = this.filtroService.eliminarDuplicadosCochesPreciosMinimos(coches);
        hash.put("size", String.valueOf(hash.size()));
        return hash;
    }


    /**
     * Metodo para retornar todas las marcas
     *
     * @return Lista de marcas
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<Marca> findAllMarcas() {
        return marcaDAO.findAll();
    }

    @Override
    public List<Consumo> findAllConsumosById(List<Integer> idsConsumo) {
        List<Consumo> consumos = this.consumoDAO.findAllByIdConsumoIn(idsConsumo);
        return this.consumoDAO.findAllByIdConsumoIn(idsConsumo);
    }

    @Override
    public HashMap<String, String> findChartId(int idCoche) {
        HashMap<String, String> chart = new HashMap<>();
        Coche coche = this.cocheDAO.findById(idCoche).orElse(null);
        if (coche != null) {
            ConsumoNormal cm = getConsumoNormal(coche);
            System.out.println(coche.getTipoMotor().getMotorCombustion().getIdMotorCombustion());
            Volumen vol = null;
            if (coche.getModelo().getVolumen() != null) {
                vol = this.volumenDAO.findById(coche.getModelo().getVolumen().getIdVolumen()).orElse(null);
            }
            if (coche.getTipoMotor().getMotorCombustion() != null) {
                int hp = coche.getTipoMotor().getMotorCombustion().getHp();
                double cilindrada = coche.getTipoMotor().getMotorCombustion().getCilindrada();
                int co2 = coche.getTipoMotor().getMotorCombustion().getEmisiones().getCO2();
                int cilindros = coche.getTipoMotor().getMotorCombustion().getCilindros();
                boolean superC = coche.getTipoMotor().getMotorCombustion().getSobrealimentacion().isSupercargador();
                boolean turbo = coche.getTipoMotor().getMotorCombustion().getSobrealimentacion().isTurbo();
                if (superC && turbo) {
                    chart.put("Sobrealimentacion", "Turbo y Supercargador");
                } else if (superC) {
                    chart.put("Sobrealimentacion", "Supercargador");
                } else if (turbo) {
                    chart.put("Sobrealimentacion", "Turbo");
                } else {
                    chart.put("Sobrealimentacion", "Atmosférico");
                }
                chart.put("Potencia", String.valueOf(hp));
                chart.put("Cilindrada", String.valueOf(cilindrada));
                chart.put("CO2", String.valueOf(co2));
                chart.put("Cilindros", String.valueOf(cilindros));
            }
            if (vol != null) {
                if (vol.getVolumen2p() != null) {
                    chart.put("Volumen Habitaculo 2 Puertas", String.valueOf(vol.getVolumen2p().getVolumenHabitaculo()));
                    chart.put("Volumen Maletero 2 Puertas", String.valueOf(vol.getVolumen2p().getVolumenMaletero()));
                }
                if (vol.getVolumen4p() != null) {
                    chart.put("Volumen Habitaculo 4 Puertas", String.valueOf(vol.getVolumen4p().getVolumenHabitaculo()));
                    chart.put("Volumen Maletero 4 Puertas", String.valueOf(vol.getVolumen4p().getVolumenMaletero()));
                }
                if (vol.getVolumenHatchback() != null) {
                    chart.put("Volumen Habitaculo Hatchback", String.valueOf(vol.getVolumenHatchback().getVolumenHabitaculo()));
                    chart.put("Volumen Maletero Hatchback", String.valueOf(vol.getVolumenHatchback().getVolumenMaletero()));
                }
            }
            chart.put("ConsumoMedio", String.valueOf(cm.getCombinado()));
            chart.put("Precio", String.valueOf(coche.getPrecio()));
            /*TODO tipos de sobrealimentaciones mas frecuentes
                si tiene motor electrico
                si lo tiene, sus caracteristicas (potencias W y caballos CV, tiempo de carga)
            */
            chart.put("size",String.valueOf(chart.size()));
            return chart;
        } else {
            return null;
        }
    }

    @Override
    public HashMap<String, String> findChartSemejantesId(int idCoche) {
        //Todo semejantes por precio
        HashMap<String, String> chart = new HashMap<>();
        Coche cocheSeleccionado = this.cocheDAO.findById(idCoche).orElse(null);
        if (cocheSeleccionado != null) {
            int precio = cocheSeleccionado.getPrecio();
            // Busca todos los coches que esten en el margen de precio
            List<Coche> coches = this.cocheDAO.findAllByPrecioIsGreaterThanEqualAndPrecioIsLessThanEqualAndAndCarroceria_Carroceria(
                    (int) (precio * (1 - MARGEN)), (int) (precio * (1 + MARGEN)), cocheSeleccionado.getCarroceria().getCarroceria());
            System.out.println("Carroceria = "+cocheSeleccionado.getCarroceria().getCarroceria());
            List<Coche> aux = new ArrayList<>(); // Variable para eliminar los coches del array que coinciden con el modelo actual
            AtomicReference<Double> mediaPrecio = new AtomicReference<>((double) 0);
            AtomicReference<Double> mediaConsumoMix = new AtomicReference<>((double) 0);
            AtomicReference<Double> hp = new AtomicReference<>((double) 0);
            AtomicReference<Double> cilindrada = new AtomicReference<>((double) 0);
            AtomicReference<Double> co2 = new AtomicReference<>((double) 0);
            HashMap<String, Double> destacados = new HashMap<>();
            HashMap<String, String> destacadosNombre = new HashMap<>();
            ArrayList<HashMap<Integer, Integer>> ids = new ArrayList<>();
            HashMap<Integer, Integer> idsConsumoCoche = new HashMap<>();
            HashMap<Integer, Integer> idsMotorCoche = new HashMap<>();
            HashMap<Integer, Integer> idsEmisionesCoche = new HashMap<>();

            ids.add(idsConsumoCoche);  // 0
            ids.add(idsMotorCoche);    // 1
            ids.add(idsEmisionesCoche);// 2

            destacados.put("cochePrecioMin",MAX);
            destacados.put("cochePrecioMax",MIN);
            destacados.put("cocheConsumoMin",MAX);
            destacados.put("cocheConsumoMax",MIN);
            destacados.put("cochePotenciaMin",MAX);
            destacados.put("cochePotenciaMax",MIN);
            destacados.put("cocheEmisionesMin",MAX);
            destacados.put("cocheEmisionesMax",MIN);

            List<Integer> idsConsumo = new ArrayList<>();
            List<Integer> idsEmisiones = new ArrayList<>();
            List<Integer> idsTipoMotor = new ArrayList<>();
            List<Integer> idsMotorCombustion = new ArrayList<>();
            coches.forEach(coche -> {
                if (coche.getModelo().getIdModelo() != cocheSeleccionado.getModelo().getIdModelo()) {
                    int idConsumo = coche.getConsumo().getIdConsumo();
                    int idMotor = coche.getTipoMotor().getIdTipoMotor();
                    aux.add(coche);
                    mediaPrecio.updateAndGet(v -> (v + coche.getPrecio()));
                    // Compara y actualiza los valores minimo y maximo encotrados de precio
                    if(coche.getPrecio()< destacados.get("cochePrecioMin")){
                      destacados.put("cochePrecioMin", (double) coche.getPrecio());
                      destacados.put("idPrecioMin", (double) coche.getIdCoche());
                      destacadosNombre.put("nCochePrecioMin", coche.getModelo().getModelo()+"/"+coche.getMarca().getMarcaCoche());
                    }
                    if(coche.getPrecio()> destacados.get("cochePrecioMax")){
                        destacados.put("cochePrecioMax", (double) coche.getPrecio());
                        destacados.put("idPrecioMax", (double) coche.getIdCoche());
                        destacadosNombre.put("nCochePrecioMax", coche.getModelo().getModelo()+"/"+coche.getMarca().getMarcaCoche());
                    }
                    ids.get(0).put(coche.getIdCoche(), idConsumo);
                    idsConsumo.add(idConsumo);
                    ids.get(1).put(coche.getIdCoche(), idMotor);
                    idsTipoMotor.add(idMotor);

                }
            });
            // Compara y actualiza los valores minimo y maximo encotrados de consumo
            this.consumoNormalDAO.findAllById(idsConsumo).forEach(consumoNormal -> {
                if(consumoNormal.getCombinado()< destacados.get("cocheConsumoMin")){
                    destacados.put("cocheConsumoMin", consumoNormal.getCombinado());
                    destacados.put("idConsumoMin", (double)consumoNormal.getIdConsumoNormal());
                }
                if(consumoNormal.getCombinado()> destacados.get("cocheConsumoMax")){
                    destacados.put("cocheConsumoMax", consumoNormal.getCombinado());
                    destacados.put("idConsumoMax", (double)consumoNormal.getIdConsumoNormal());
                }
                mediaConsumoMix.updateAndGet(v -> v + consumoNormal.getCombinado());
            });
            this.tipoMotorDAO.findAllById(idsTipoMotor).forEach(tm->{
                idsMotorCombustion.add(tm.getMotorCombustion().getIdMotorCombustion());
            });
            this.motorCombustionDAO.findAllById(idsMotorCombustion).forEach(motorCombustion -> {
                int idEmisiones = motorCombustion.getEmisiones().getIdEmisiones();
                hp.updateAndGet(v -> v + motorCombustion.getHp());
                cilindrada.updateAndGet(v -> v + motorCombustion.getCilindrada());
                ids.get(2).put(ids.get(1).get(motorCombustion.getIdMotorCombustion()), idEmisiones);
                idsEmisiones.add(idEmisiones);
                if(motorCombustion.getHp()< destacados.get("cochePotenciaMin")){
                    destacados.put("cochePotenciaMin", (double) motorCombustion.getHp());
                    destacados.put("idMotorCMin", (double)motorCombustion.getIdMotorCombustion());
                }
                if(motorCombustion.getHp()> destacados.get("cochePotenciaMax")){
                    destacados.put("cochePotenciaMax", (double) motorCombustion.getHp());
                    destacados.put("idMotorCMax", (double)motorCombustion.getIdMotorCombustion());
                }
            });
            this.emisionesDAO.findAllById(idsEmisiones).forEach(emisiones -> {
                co2.updateAndGet(v -> v + emisiones.getCO2());
                if(emisiones.getCO2()< destacados.get("cocheEmisionesMin")){
                    destacados.put("cocheEmisionesMin", (double) emisiones.getCO2());
                    destacados.put("idEmisionesMin", (double) emisiones.getIdEmisiones());
                }
                if(emisiones.getCO2()> destacados.get("cocheEmisionesMax")){
                    destacados.put("cocheEmisionesMax", (double) emisiones.getCO2());
                    destacados.put("idEmisionesMax", (double) emisiones.getIdEmisiones());
                }
            });
            int mediaP = (int) round(mediaPrecio.updateAndGet(aDouble -> aDouble / aux.size()));
            int mediaHP = (int) round(hp.updateAndGet(aDouble -> aDouble / aux.size()));
            double mediaCilindrada = round(cilindrada.updateAndGet(aDouble -> aDouble / aux.size())*10);
            mediaCilindrada/=10;
            int mediaCO2 = (int) round(co2.updateAndGet(aDouble -> aDouble / aux.size()));
            double mediaConsMix = round(mediaConsumoMix.updateAndGet(aDouble -> aDouble / aux.size())*100);
            mediaConsMix/=100;

            chart.put("mediaPrecio",String.valueOf(mediaP));
            chart.put("mediaPotencia",String.valueOf(mediaHP));
            chart.put("mediaCilindrada",String.valueOf(mediaCilindrada));
            chart.put("mediaEmisiones",String.valueOf(mediaCO2));
            chart.put("mediaConsumo",String.valueOf(mediaConsMix));
//            chart.put("modeloPrecioMin",destacadosNombre.get("nCochePrecioMin"));
//            chart.put("modeloPrecioMax",destacadosNombre.get("nCochePrecioMax"));
            List<Integer> idsN;// = new ArrayList<>();
//            idsN.add(Math.round(Float.parseFloat(destacadosNombre.get("idPrecioMin"))));
//            idsN.add(Math.round(Float.parseFloat(destacadosNombre.get("idPrecioMax"))));
//            List<Coche> cocheMinMax = this.cocheDAO.findAllByIdCocheIn(idsN);
//            chart.put("modeloPrecioMinImage",);
//            chart.put("modeloPrecioMaxImage",);


            String[] chartP={"modeloPrecio","modeloConsumo", "modeloPotencia","modeloEmisiones"};
            String[] dest = {"idPrecio","idConsumo", "idMotorC","idEmisiones"};
            // Se añaden al chart los modelos con valores minimo y maximo en consumo, potencia y emisiones
            for (int i = 0;i<4;i++){
                idsN = new ArrayList<>();
                if(i==0){
                    idsN.add((int) Math.round(destacados.get(dest[i] + "Min")));
                    idsN.add((int) Math.round(destacados.get(dest[i] + "Max")));
                }else{
                    idsN.add(ids.get(i-1).get((int) Math.round(destacados.get(dest[i] + "Min"))));
                    idsN.add(ids.get(i-1).get((int) Math.round(destacados.get(dest[i] + "Max"))));
                }
                List<Coche> cocheMinMax = this.cocheDAO.findAllByIdCocheIn(idsN);
                if(cocheMinMax.size()==1){ // Caso en el que la lista de adsN sean ambos el mismo id
                    cocheMinMax.add(this.cocheDAO.findById(idsN.get(0)).orElse(null));
                }
                chart.put(chartP[i]+"Min",cocheMinMax.get(0).getModelo().getModelo()+"/"+cocheMinMax.get(0).getMarca().getMarcaCoche());
                chart.put(chartP[i]+"Max",cocheMinMax.get(1).getModelo().getModelo()+"/"+cocheMinMax.get(1).getMarca().getMarcaCoche());
                chart.put(chartP[i]+"MinImage",cocheMinMax.get(0).getModelo().getImagen());
                chart.put(chartP[i]+"MaxImage",cocheMinMax.get(1).getModelo().getImagen());
                chart.put("id"+chartP[i]+"Min",String.valueOf(cocheMinMax.get(0).getModelo().getIdModelo()));
                chart.put("id"+chartP[i]+"Max",String.valueOf(cocheMinMax.get(1).getModelo().getIdModelo()));
            }

            chart.put("size",String.valueOf(chart.size()));
            return chart;
        } else {
            return null;
        }
    }

    @Override
    public List<MotorCombustion> getAllMotorCombustionByIds(List<Integer> idsMotorCombustion) {
        return this.motorCombustionDAO.findByIdMotorCombustionIn(idsMotorCombustion);
    }

    /**
     * Metodo para retornar todos los coches segun el modelo especificado
     *
     * @param idModelo
     * @return
     */
    @Override
    public List<Coche> findAllCocheByIdModelo(int idModelo) {
        return cocheDAO.findByModelo_IdModelo(idModelo);
    }

    /**
     * Metodo para buscar en bbdd todas las carrocerias
     *
     * @return Lista de carrocerias
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<Carroceria> findAllCarrocerias() {
        return carroceriaDAO.findAll();
    }

    @Override
    public HashMap<String,String> findImagen(String modelo, String marca) {
        HashMap<String,String> map = new HashMap();
        map.put("imagen",this.modeloDAO.findByModeloAndMarca_MarcaCoche(modelo, marca).getImagen());
        return map;
    }


    /**
     * Metodo para retornar un coche segun su id
     *
     * @param id Id del coche a retornar
     * @return Coche con id especificado
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public Coche findCocheById(int id) {
        // Lo retorna si lo encuentra y en caso contrario retorna null
        return cocheDAO.findById(id).orElse(null);
    }

    /**
     * Metodo para retornar un modelo segun su id
     *
     * @param id Id del modelo a retornar
     * @return Modelo con id especificado
     */
    @Override
    public Modelo findModeloById(int id) {
        return modeloDAO.findById(id).orElse(null);
    }


    /**
     * Metodo para retornar una pagina con modelos
     *
     * @param pageable Pagina de modelos a buscar
     * @return Pagina con los modelos
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public Page<Modelo> findAllModelos(Pageable pageable) {
        return modeloDAO.findAll(pageable);
    }

    /**
     * Metodo para retornar una pagina con modelos segun la marca especificada
     *
     * @param pageable Pagina de modelos a buscar
     * @return Pagina con los modelos
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public Page<Modelo> findAllModelosPorMarca(Pageable pageable, int idMarca) {
        // Lo retorna si lo encuentra y en caso contrario retorna null
        return modeloDAO.findAllByMarca_IdMarca(pageable, idMarca);
    }


    /**
     * Metodo para retornar todos los modelos segun la marca especificada
     *
     * @return Lista con los modelos
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<Modelo> findAllModelosPorMarca(int idMarca) {
        // Lo retorna si lo encuentra y en caso contrario retorna null
        return modeloDAO.findAllByMarca_IdMarca(idMarca);
    }


    /**
     * Metodo para retornar todos los motores de combustion
     *
     * @return Lista de motores de combustion
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<MotorCombustion> findAllMotorCombustion() {
        return motorCombustionDAO.findAll();
    }


    /**
     * Metodo para retornar un motor de combustion segun su id
     *
     * @param id Id del motor de combustion a retornar
     * @return Motor de combustion con id especificado
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public MotorCombustion findMotorCombustionById(int id) {
        // Lo retorna si lo encuentra y en caso contrario retorna null
        return motorCombustionDAO.findById(id).orElse(null);
    }


    /**
     * Metodo para retornar todos los motores de combustion
     *
     * @return Lista de motores electricos
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<MotorElectrico> findAllMotorElectrico() {
        return motorElectricoDAO.findAll();
    }


    /**
     * Metodo para retornar un motor de combustion segun su id
     *
     * @param id Id del motor de combustion a retornar
     * @return Motor de combustion con id especificado
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public MotorElectrico findMotorElectricoById(int id) {
        // Lo retorna si lo encuentra y en caso contrario retorna null
        return motorElectricoDAO.findById(id).orElse(null);
    }


    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public ArrayList<Double> generateDataVolumenCarroceria(int idCarroceria) {
        // Busca todos los coches con la carroceria especificada
        List<Coche> coches = cocheDAO.findAllModeloDistinctByCarroceria_IdCarroceria(idCarroceria);
        List<Coche> cochesAux = new ArrayList<>();
        ArrayList<Integer> idVolumenes = new ArrayList<>();

        //Obtenemos los volumenes_id no nulos de de los modelos con la carroceria especificada
        for (Coche coche : coches) {
            Volumen volumen = coche.getModelo().getVolumen();
            if (volumen != null) {
                int idVol = volumen.getIdVolumen();
                if (!idVolumenes.contains(idVol)) {
                    idVolumenes.add(idVol);
                    cochesAux.add(coche);
                }
            }
        }
        idVolumenes.size();
        AtomicInteger elementos2p = new AtomicInteger();
        AtomicInteger elementos4p = new AtomicInteger();
        AtomicInteger elementosH = new AtomicInteger();
        AtomicReference<Double> vol2pHabitaculo = new AtomicReference<>((double) 0);
        AtomicReference<Double> vol2pMaletero = new AtomicReference<>((double) 0);
        AtomicReference<Double> vol4pHabitaculo = new AtomicReference<>((double) 0);
        AtomicReference<Double> vol4pMaletero = new AtomicReference<>((double) 0);
        AtomicReference<Double> volHHabitaculo = new AtomicReference<>((double) 0);
        AtomicReference<Double> volHMaletero = new AtomicReference<>((double) 0);
        cochesAux.forEach(coche -> {

            if (coche.getModelo().getVolumen().getVolumen2p() != null) {
                vol2pHabitaculo.updateAndGet(v -> (v + coche.getModelo().getVolumen().getVolumen2p().getVolumenHabitaculo()));
                vol2pMaletero.updateAndGet(v -> (v + coche.getModelo().getVolumen().getVolumen2p().getVolumenMaletero()));
                elementos2p.getAndIncrement();
            }
            if (coche.getModelo().getVolumen().getVolumen4p() != null) {
                vol4pHabitaculo.updateAndGet(v -> (v + coche.getModelo().getVolumen().getVolumen4p().getVolumenHabitaculo()));
                vol4pMaletero.updateAndGet(v -> (v + coche.getModelo().getVolumen().getVolumen4p().getVolumenMaletero()));
                elementos4p.getAndIncrement();
            }
            if (coche.getModelo().getVolumen().getVolumenHatchback() != null) {
                volHHabitaculo.updateAndGet(v -> (v + coche.getModelo().getVolumen().getVolumenHatchback().getVolumenHabitaculo()));
                volHMaletero.updateAndGet(v -> (v + coche.getModelo().getVolumen().getVolumenHatchback().getVolumenMaletero()));
                elementosH.getAndIncrement();
            }

        });


        double mediaMaletero2p = round(vol2pMaletero.updateAndGet(aDouble -> aDouble / elementos2p.doubleValue()) * 100);
        mediaMaletero2p /= 100;
        double mediaHabitaculo2p = round(vol2pHabitaculo.updateAndGet(aDouble -> aDouble / elementos2p.doubleValue()) * 100);
        mediaHabitaculo2p /= 100;
        double mediaMaletero4p = round(vol4pMaletero.updateAndGet(aDouble -> aDouble / elementos4p.doubleValue()) * 100);
        mediaMaletero4p /= 100;
        double mediaHabitaculo4p = round(vol4pHabitaculo.updateAndGet(aDouble -> aDouble / elementos4p.doubleValue()) * 100);
        mediaHabitaculo4p /= 100;
        double mediaMaleteroH = round(volHMaletero.updateAndGet(aDouble -> aDouble / elementosH.doubleValue()) * 100);
        mediaMaleteroH /= 100;
        double mediaHabitaculoH = round(volHHabitaculo.updateAndGet(aDouble -> aDouble / elementosH.doubleValue()) * 100);
        mediaHabitaculoH /= 100;

        ArrayList<Double> medias = new ArrayList<>();
        medias.add(mediaMaletero2p);
        medias.add(mediaHabitaculo2p);
        medias.add(mediaMaletero4p);
        medias.add(mediaHabitaculo4p);
        medias.add(mediaMaleteroH);
        medias.add(mediaHabitaculoH);
        return medias;

//        for (int i = 0; i < idVolumenes.size(); i++) {
//
//        }
////        //Dame todos los volumenes que coinciden con esoso volumenes_id
////        System.out.println(coches);
//        List<Volumen> vols = volumenDAO.findAll();// vol = volumenDAO.findById(idVolumenes.get(0)).orElse(null);
//        List<Volumen> volumenes = volumenDAO.findAllById(idVolumenes);
//        System.out.println(vols.get(0).getVolumen4p().getVolumenHabitaculo());
//        return coches.get(0);
    }



    private ConsumoNormal getConsumoNormal(Coche coche) {
        int idConsumo = coche.getConsumo().getIdConsumo();
        Consumo consumo = this.consumoDAO.findById(idConsumo).orElse(null);
        assert consumo != null;
        return this.consumoNormalDAO.findById(consumo.getIdConsumoNormal().getIdConsumoNormal()).orElse(null);
    }
}
