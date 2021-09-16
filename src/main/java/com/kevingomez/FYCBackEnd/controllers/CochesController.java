package com.kevingomez.FYCBackEnd.controllers;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IChartService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.entity.Coches.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/coches")
public class CochesController {
    private static final Logger log = LoggerFactory.getLogger(CochesController.class);

    @Autowired
    private ICocheService cocheService;

    @Autowired
    private IChartService chartService;


    /**
     * Metodo para retornar todos los coches
     * @param idModelo Id del modelo
     * @return Lista de Coches
     */
    @GetMapping("modelo/{idModelo}")
    public List<Coche> getCochesByModelo(@PathVariable int idModelo) {
        log.info("Buscando todos los coches con el modelo id "+idModelo);
        return cocheService.findAllCocheByIdModelo(idModelo);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("save")
    public ResponseEntity<?> save(@RequestBody Coche coche) {
        log.info("Guardando el coche "+coche.getIdCoche()+" en la base de datos ");
        Map<String, Object> response = new HashMap<>();
        Coche cocheSaved;
        try {
            cocheSaved = cocheService.save(coche);
        } catch (DataAccessException e) {
            response.put("error", "Error al insertar al usuario en la base de datos.");
            log.error(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Vehiculo guardado correctamente.");
        response.put("coche", cocheSaved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Metodo para retornar el coche segun el id especificado
     *
     * @param id ID del coche
     * @return Coche con el id espeficicado
     */
    @GetMapping("{id}")
    public Coche getCocheById(@PathVariable int id) {
        log.info("Buscando coche con id "+id);
        return cocheService.findCocheById(id);
    }


    /**
     * Metodo para retornar los precios de los modelos solicitados
     * @param idsModelos Lista de modelos
     * @return HashMap con los precios
     */
    @PostMapping("precios")
    public HashMap<String, String> preciosModelos(@RequestBody List<Integer> idsModelos) {
        log.info("Buscando todos los precios de los coches segun la lista de modelos");
        return cocheService.findAllPreciosList(idsModelos);
    }


    /**
     * Metodo para buscar los tipos de motores por lista
     * @param idsTiposMotor Lista con los ids de los motores
     * @return Lista de motores
     */
    @PostMapping("tipos_motores")
    public ResponseEntity<?> findTiposMotor(@RequestBody List<Integer> idsTiposMotor) {
        Map<String, Object> response = new HashMap<>();
        log.info("Buscando los tipos de motor segun lista de ids");
        HashMap<Integer, TipoMotor> map = new HashMap<>();
        List<TipoMotor> tiposmotores = cocheService.findAllTipoMotorByIds(idsTiposMotor);
        tiposmotores.forEach(tipoMotor -> map.put(tipoMotor.getIdTipoMotor(), tipoMotor));
        response.put("tipos_motores",map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Metodo para buscar los tipos de combustibles
     * @return Lista de tipos de combustibles
     */
    @GetMapping("tipos_combustibles")
    public ResponseEntity<?> findTiposCombustibles() {
        Map<String, Object> response = new HashMap<>();
        log.info("Buscando los tipos de combustibles");
        List<TipoCombustible> tiposCombustibles = cocheService.findAllTipoCombustible();
        response.put("tipos_combustibles",tiposCombustibles);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Metodo para buscar las normativas europeas
     * @return Lista con las normativas europeas
     */
    @GetMapping("normativas_consumos")
    public ResponseEntity<?> findNormativasConsumos() {
        Map<String, Object> response = new HashMap<>();
        log.info("Buscando las normativas de los consumos");
        List<TipoEmisiones> normativas = cocheService.findAllNormativasConsumos();
        response.put("normativas",normativas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * *****************************************************************************************************************
     *                                                    Consumo
     * *****************************************************************************************************************
     */

    /**
     * Metodo para obtener todos los consumos segun una lista de ids
     * @param idsConsumos Lista de ids buscados
     * @return Lista de ids buscados
     */
    @PostMapping("consumo")
    public List<Consumo> findAllConsumosById(@RequestBody List<Integer>idsConsumos){
        log.info("Buscando los consumos segun una lista de ids");
        return this.cocheService.findAllConsumosById(idsConsumos);
    }

    /**
     * Metodo para guardar el consumo
     * @param consumo Consumo a guardar
     * @return Consumo guardado
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("consumo/save")
    public ResponseEntity<?> saveConsumo(@RequestBody Consumo consumo){
        log.info("Guardando el consumo con id "+consumo.getIdConsumo()+" en la base de datos.");
        Map<String, Object> response = new HashMap<>();
        Consumo consumoSaved;
        try {
            consumoSaved = this.cocheService.saveConsumo(consumo);
        } catch (DataAccessException e) {
            response.put("error", "Error al guardar el consumo en la base de datos.");
            log.error(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Vehiculo guardado correctamente.");
        response.put("consumo", consumoSaved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Metodo para buscar el consumo por id
     * @param id Id del consumo a buscasr
     * @return Consumo encontrado
     */
    @GetMapping("consumo/{id}")
    public Consumo findConsumoById(@PathVariable Integer id){
        log.info("Buscando el consumos con id "+ id);
        return this.cocheService.findConsumoById(id);
    }

    /*
     * *****************************************************************************************************************
     *                                                 Motor Combustion
     * *****************************************************************************************************************
     */

    /**
     * Metodo para retornar todos los motores de combustion
     *
     * @return Lista de Motores de combustion
     */
    @GetMapping("combustion")
    public List<MotorCombustion> indexMotorCombustion() {
        log.info("Buscando todos los motores de combustion");
        return cocheService.findAllMotorCombustion();
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("combustion/save")
    public ResponseEntity<?> save(@RequestBody MotorCombustion motorCombustion) {
        log.info("Guardando el motor de combustion "+motorCombustion.getIdMotorCombustion()+" en la base de datos ");
        Map<String, Object> response = new HashMap<>();
        MotorCombustion motorCombustionSaved;
        try {
            motorCombustionSaved = cocheService.saveMotorCombustion(motorCombustion);
        } catch (DataAccessException e) {
            response.put("error", "Error al guardar el motor de combustion en la base de datos.");
            log.error(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Vehiculo guardado correctamente.");
        response.put("motorCombustion", motorCombustionSaved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Metodo para retornar el motor de combustion segun el id especificado
     *
     * @param id ID del Motor de combustion
     * @return Motor de combustion con el id espeficicado
     */
    @GetMapping("combustion/{id}")
    public MotorCombustion getMotorCombustionById(@PathVariable int id) {
        log.info("Buscando MotorCombustion con id "+id);
        return cocheService.findMotorCombustionById(id);
    }

    /**
     * Metodo para retornar el motor de combustion segun el id de TipoMotor especificado
     *
     * @param id ID del Tipo Motor
     * @return Motor de combustion
     */
    @GetMapping("tipo_motor/{id}")
    public TipoMotor getTipoMotorById(@PathVariable int id) {
        log.info("Buscando TipoMotor con id "+id);
        return cocheService.findTipoMotorById(id);
    }
    /**
     * Metodo para retornar la lista de motores de combustion segun los ids especificados
     *
     * @param idsMotorCombustion IDS de los Motores de combustion
     * @return Motor de combustion con el id espeficicado
     */
    @PostMapping("motorescombustion")
    public List<MotorCombustion> getAllMotorCombustionByIds(@RequestBody List<Integer> idsMotorCombustion) {
        log.info("Buscando motores de combustion por lista de ids");
        return cocheService.getAllMotorCombustionByIds(idsMotorCombustion);
    }

    /**
     * Metodo para retornar las emisiones de un motor segun el id especificado
     *
     * @param id ID de las emisiones
     * @return Emisiones
     */
    @GetMapping("emisiones/{id}")
    public Emisiones getEmisionesById(@PathVariable int id) {
        log.info("Buscando motores de combustion por lista de ids");
        return cocheService.getEmisionesById(id);
    }

    /*
     * *****************************************************************************************************************
     *                                                 Motor Electrico
     * *****************************************************************************************************************
     */

    /**
     * Metodo para retornar todos los motores electricos
     *
     * @return Lista de motores electricos
     */
    @GetMapping("electrico")
    public List<MotorElectrico> indexMotorElectrico() {
        log.info("Buscando todos los motores electricos");
        return cocheService.findAllMotorElectrico();
    }

    /**
     * Metodo para retornar el motor electrico segun el id especificado
     *
     * @param id ID del Motor electrico
     * @return Motor electrico con el id espeficicado
     */
    @GetMapping("electrico/{id}")
    public MotorElectrico getMotorElectricoById(@PathVariable int id) {
        log.info("Buscando MotorCombustion con id "+id);
        return cocheService.findMotorElectricoById(id);
    }

    /**
     * Metodo para guardar el motor electrico
     *
     * @param motorElectrico motorElectrico
     * @return Motor electrico guardado
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("electrico/save")
    public ResponseEntity<?> saveMotorElectrico(@RequestBody MotorElectrico motorElectrico) {
        log.info("Guardando motor electrico");
        Map<String, Object> response = new HashMap<>();
        MotorElectrico motorElectricoSaved;
        try {
            motorElectricoSaved = cocheService.saveMotorElectrico(motorElectrico);
        } catch (DataAccessException e) {
            response.put("error", "Error al guardar el motor electrico en la base de datos.");
            log.error(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Vehiculo guardado correctamente.");
        response.put("motorElectrico", motorElectricoSaved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }




    /*
     * *****************************************************************************************************************
     *                                                 Volumen
     * *****************************************************************************************************************
     */

    /**
     * Metodo para retornar el volumen segun el id especificado
     *
     * @param id ID del volumen
     * @return Volumen con el id espeficicado
     */
    @GetMapping("volumen/{id}")
    public Volumen getVolumenById(@PathVariable int id) {
        log.info("Buscando volumen con id "+id);
        return cocheService.findVolumenById(id);
    }

    /**
     * Metodo para guardar el volumen
     *
     * @param volumen Volumen a guardar
     * @return Volumen guardado
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("volumen/save")
    public ResponseEntity<?> saveVolumen(@RequestBody Volumen volumen) {
        log.info("Guardando volumen");
        Map<String, Object> response = new HashMap<>();
        Volumen volumenSaved;
        try {
            volumenSaved = cocheService.saveVolumen(volumen);
        } catch (DataAccessException e) {
            response.put("error", "Error al guardar el volumen en la base de datos.");
            log.error(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Vehiculo guardado correctamente.");
        response.put("volumen", volumenSaved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /*
     * *****************************************************************************************************************
     *                                                 Gestion
     * *****************************************************************************************************************
     */
//    /**
//     * Metodo para
//     *
//     * @param idCarroceria Id de la carroceria a gestionar
//     * @return Coche encontrados
//     */
//
//    @GetMapping("generardatos/volumen/carroceria/{idCarroceria}")
//    public Coche generateDataVolumenCarroceria(@PathVariable int idCarroceria){
//        log.info("Generando datos de volumen para idCarroceria: "+idCarroceria);
////        return cocheService.generateDataVolumenCarroceria(idCarroceria);
//        return null;
//    }

    @GetMapping("chart/{idCoche}")
    public HashMap<String, String> getChartByIdCoche(@PathVariable int idCoche){
        log.info("Buscamos valores de coche id "+idCoche);
        return this.cocheService.findChartId(idCoche);
    }
    @GetMapping("chartVolumen")
    public HashMap<String, Object> getChartVolumen(){
        log.info("Buscamos datos de grafica de volumenes");
        return this.chartService.getChartVolumen();
    }

    @GetMapping("chartsemejantes/{idCoche}/{filtro}")
    public HashMap<String, String> getChartSemejantesByIdCoche(@PathVariable int idCoche, @PathVariable String filtro){
        log.info("Buscamos valores medios de coches semejantes a coche id "+idCoche+ " por "+filtro);
        return this.cocheService.findChartSemejantesId(idCoche, filtro);
    }


}
