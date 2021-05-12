package com.kevingomez.FYCBackEnd.controllers;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.entity.Coches.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/coches")
public class CochesController {
    private static final Logger log = LoggerFactory.getLogger(CochesController.class);

    @Autowired
    private ICocheService cocheService;


    /**
     * Metodo para retornar todos los coches
     *
     * @return Lista de Coches
     */
    @GetMapping("modelo/{idModelo}")
    public List<Coche> getCochesByModelo(@PathVariable int idModelo) {
        log.info("Buscando todos los coches con el modelo id "+idModelo);
        return cocheService.findAllCocheByIdModelo(idModelo);
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
     * @param idsModelos
     * @return
     */
    @PostMapping("precios")
    public HashMap<String, String> preciosModelos(@RequestBody List<Integer> idsModelos) {
        log.info("Buscando todos los precios de los coches segun la lista de modelos");
        return cocheService.findAllPreciosList(idsModelos);
    }


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
     * *****************************************************************************************************************
     *                                                    Consumo
     * *****************************************************************************************************************
     */

    @PostMapping("consumo")
    public List<Consumo> findAllConsumosById(@RequestBody List<Integer>idsConsumos){
        log.info("Buscando los consumos segun una lista de ids");
        return this.cocheService.findAllConsumosById(idsConsumos);
    }

    /**
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
     * *****************************************************************************************************************
     *                                                 Gestion
     * *****************************************************************************************************************
     */
    /**
     * Metodo para
     *
     * @param idCarroceria Id de la carroceria a gestionar
     * @return
     */

    @GetMapping("generardatos/volumen/carroceria/{idCarroceria}")
    public Coche generateDataVolumenCarroceria(@PathVariable int idCarroceria){
        log.info("Generando datos de volumen para idCarroceria: "+idCarroceria);
//        return cocheService.generateDataVolumenCarroceria(idCarroceria);
        return null;
    }

    @GetMapping("chart/{idCoche}")
    public HashMap<String, String> getChartByIdCoche(@PathVariable int idCoche){
        log.info("Buscamos valores de coche id "+idCoche);
        return this.cocheService.findChartId(idCoche);
    }

    @GetMapping("chartsemejantes/{idCoche}")
    public HashMap<String, String> getChartSemejantesByIdCoche(@PathVariable int idCoche){
        log.info("Buscamos valores medios de coches semejantes a coche id "+idCoche);
        return this.cocheService.findChartSemejantesId(idCoche);
    }


}
