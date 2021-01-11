package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IFiltrosService;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.*;
import com.kevingomez.FYCBackEnd.models.entity.*;
import com.kevingomez.FYCBackEnd.models.filters.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Math.round;

@Service
public class CocheService implements ICocheService {
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
    private IMotorCombustionDAO motorCombustionDAO;
    @Autowired
    private IMotorElectricoDAO motorElectricoDAO;

    private static Logger log = LoggerFactory.getLogger(CocheService.class);

    /**
     * Metodo para retornar todos los coches
     *
     * @return Lista de coches
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<Coche> findAllCoches() {
        return cocheDAO.findAll();
    }


    /**
     * Metodo para retornar todos los coches
     *
     * @return Lista de coches
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<Marca> findAllMarcas() {
        return marcaDAO.findAll();
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
     * Metodo para retornar una pagina con coches
     *
     * @param pageable Pagina de coches a buscar
     * @return Pagina con los coches
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public Page<Coche> findAllCoches(Pageable pageable) {
        // Lo retorna si lo encuentra y en caso contrario retorna null
        return cocheDAO.findAll(pageable);
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
        // Lo retorna si lo encuentra y en caso contrario retorna null
        Page p = modeloDAO.findAll(pageable);
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
    public Coche generateDataVolumenCarroceria(int idCarroceria) {
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

        for (int i = 0; i < idVolumenes.size(); i++) {

        }
//        //Dame todos los volumenes que coinciden con esoso volumenes_id
//        System.out.println(coches);
        List<Volumen> vols = volumenDAO.findAll();// vol = volumenDAO.findById(idVolumenes.get(0)).orElse(null);
        List<Volumen> volumenes = volumenDAO.findAllById(idVolumenes);
        System.out.println(vols.get(0).getVolumen4p().getVolumenHabitaculo());
        return coches.get(0);
    }

    /**
     * Metodo para retornar todos los logos de las marcas
     *
     * @return Logos de las marcas
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public Resource getMarcaLogo(int idMarca) {
        log.info("Recuperando los logos de las marcas");
        Marca marca = marcaDAO.findById(idMarca).orElse(null);
        Resource resource = null;
        if(marca!=null) {
            Path filePath = Paths.get("src/main/resources/static/images/marcas").resolve(marca.getMarcaCoche() + ".png").toAbsolutePath();
            try {
                resource = new UrlResource(filePath.toUri());
            } catch (MalformedURLException e) {
                log.error("Error al buscar la marca " + idMarca + ": " + e);
                e.printStackTrace();
            }
        }
        return resource;
    }
}
