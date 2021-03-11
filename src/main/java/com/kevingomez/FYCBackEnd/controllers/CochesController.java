package com.kevingomez.FYCBackEnd.controllers;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IFiltrosService;
import com.kevingomez.FYCBackEnd.models.entity.Coches.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@CrossOrigin(origins = {"http://localhost:4200"})

//@CrossOrigin(origins = {"https://findyourcarapp-96860.web.app"})
@RestController
@RequestMapping("/api")
public class CochesController {
    private static final Logger log = LoggerFactory.getLogger(CochesController.class);

    @Autowired
    private ICocheService cocheService;

    @Autowired
    private IFiltrosService filtrosService;

    /**
     * *****************************************************************************************************************
     *                                                      Coches
     * *****************************************************************************************************************
     */

    //TODO este metodo nose usa
    /**
     * Metodo para retornar todos los coches
     *
     * @return Lista de Coches
     */
    @GetMapping("coches/modelo/{idModelo}")
    public List<Coche> getCochesByModelo(@PathVariable int idModelo) {
        log.info("Buscando todos los coches con el modelo id "+idModelo);
        return cocheService.findAllCocheByIdModelo(idModelo);
    }

    //TODO optimizar marcas ya k tambien se envian desde marcas linkshared
    /**
     * Metodo para retornar todos las marcas
     *
     * @return Lista de Marcas
     */
    @GetMapping("modelos/marcas")
    public List<Marca> indexMarcas() {
        log.info("Buscando todos las marcas");
        return cocheService.findAllMarcas();
    }

    /**
     * Metodo para retornar el coche segun el id especificado
     *
     * @param id ID del coche
     * @return Coche con el id espeficicado
     */
    @GetMapping("coches/{id}")
    public Coche getCocheById(@PathVariable int id) {
        log.info("Buscando coche con id "+id);
        return cocheService.findCocheById(id);
    }


    @PostMapping("modelos/precios")
    public HashMap<String, String> preciosModelos(@RequestBody List<Integer> idsModelos) {
        log.info("Buscando todos los precios de los coches segun la lista de modelos");
        return cocheService.findAllPreciosList(idsModelos);
    }

    /**
     * Metodo para retornar el modelo segun el id especificado
     *
     * @param id ID del modelo
     * @return Modelo con el id espeficicado
     */
    @GetMapping("modelo/{id}")
    public ResponseEntity<?> getModeloById(@PathVariable int id) {
        log.info("Buscando modelo con id "+id);
        Modelo modelo;
        Map<String, Object> response = new HashMap<>();
        try {
            modelo = cocheService.findModeloById(id);
            if(modelo.getImagen().equals("")){
                modelo.setImagen("defaultImage.jpg");
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMostSpecificCause().getMessage());
            response.put("errorEspecifico", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (modelo == null) {
            response.put("mensaje", "El modelo ID: ".concat(Integer.toString(id).concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(modelo, HttpStatus.OK);
    }


    /**
     * Metodo para retornar una pagina con un numero de modelos
     *
     * @return Pagina de modelos
     */
    @GetMapping("modelos/{pageSize}/page/{page}")
    public Page<Modelo> findModelosPage(@PathVariable Integer pageSize, @PathVariable Integer page) {
        log.info("Buscando la pagina "+page+" de modelos con "+pageSize+" elementos");
        return cocheService.findAllModelos(PageRequest.of(page, pageSize));
    }

    /**
     * Metodo para retornar una pagina con un numero de modelos
     *
     * @return Pagina de modelos por marcas
     */
    @GetMapping("modelospormarca/{pageSize}/idmarca/{idmarca}/page/{page}")
    public Page<Modelo> findModelosPorMarcasPage(@PathVariable Integer pageSize, @PathVariable Integer idmarca, @PathVariable Integer page) {
        log.info("Buscando la pagina "+page+" de modelos con "+pageSize+" elementos para la marca "+idmarca);
        return cocheService.findAllModelosPorMarca(PageRequest.of(page, pageSize),idmarca);
    }

    //TODO este metodo no se usa
    /**
     * Metodo para retornar todos los modelos segun la marca
     *
     * @return Modelos de la marca
     */
    @GetMapping("modelospormarca/idmarca/{idmarca}")
    public List<Modelo> findModelosPorMarca(@PathVariable Integer idmarca) {
        log.info("Buscando los modelos para la marca "+idmarca);
        return cocheService.findAllModelosPorMarca(idmarca);
    }


    /**
     * Metodo para buscar todas las carrocerias
     *
     * @return Lista con todas las carrocerias
     */
    @GetMapping("carrocerias")
    public List<Carroceria> findAllCarrocerias(){
        log.info("Buscando todas las carrocerias");
        return this.cocheService.findAllCarrocerias();
    }

    /**
     * *****************************************************************************************************************
     *                                                    Consumo
     * *****************************************************************************************************************
     */

    @PostMapping("coches/consumo")
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
    @GetMapping("coches/combustion")
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
    @GetMapping("coches/combustion/{id}")
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
    @PostMapping("/coches/motorescombustion")
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
    @GetMapping("coches/electrico")
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
    @GetMapping("coches/electrico/{id}")
    public MotorElectrico getMotorElectricoById(@PathVariable int id) {
        log.info("Buscando MotorCombustion con id "+id);
        return cocheService.findMotorElectricoById(id);
    }




    /**
     * *****************************************************************************************************************
     *                                                 Filtros
     * *****************************************************************************************************************
     */
    @PostMapping("modelos/{pageSize}/filtros/page/{page}")
    public Page<Modelo> filtrar(@PathVariable Integer page, @PathVariable Integer pageSize, @RequestBody Object filtros){
        log.info("Comenzamos a filtrar");
        log.info("----------------------------------");
        Matcher mat = Pattern.compile("\\{(.*?)}").matcher(filtros.toString());
        return this.filtrosService.filtrarModelos(PageRequest.of(page, pageSize), this.filtrosService.estructurarFiltros(mat));
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

    @GetMapping("coches/generardatos/volumen/carroceria/{idCarroceria}")
    public Coche generateDataVolumenCarroceria(@PathVariable int idCarroceria){
        log.info("Generando datos de volumen para idCarroceria: "+idCarroceria);
//        return cocheService.generateDataVolumenCarroceria(idCarroceria);
        return null;
    }

    @GetMapping("coches/chart/{idCoche}")
    public HashMap<String, String> getChartByIdCoche(@PathVariable int idCoche){
        log.info("Buscamos valores de coche id "+idCoche);
        return this.cocheService.findChartId(idCoche);
    }

    @GetMapping("coches/chartsemejantes/{idCoche}")
    public HashMap<String, String> getChartSemejantesByIdCoche(@PathVariable int idCoche){
        log.info("Buscamos valores medios de coches semejantes a coche id "+idCoche);
        return this.cocheService.findChartSemejantesId(idCoche);
    }

    @GetMapping("modelo/imagen")
    public HashMap<String,String> getChartSemejantesByIdCoche(@RequestBody HashMap<String, String> map){
        return this.cocheService.findImagen(map.get("modelo"),map.get("marca"));
    }
}
