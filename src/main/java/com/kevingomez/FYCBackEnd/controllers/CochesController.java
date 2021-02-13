package com.kevingomez.FYCBackEnd.controllers;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Impl.UploadFileService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IFiltrosService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ISubirFicheroService;
import com.kevingomez.FYCBackEnd.models.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class CochesController {
    private static Logger log = LoggerFactory.getLogger(CochesController.class);
    private int elementsForPage = 20;

    @Autowired
    private ICocheService cocheService;

    @Autowired
    private IFiltrosService filtrosService;

    @Autowired
    private ISubirFicheroService subirFicheroService;

    @Autowired
    private UploadFileService uploadFileService;

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
    public List<Coche> getCochesByModelo(@PathVariable int idModelo) {  //Peticion de tipo get
        log.info("Buscando todos los coches con el modelo id "+idModelo);
        return cocheService.findAllCocheByIdModelo(idModelo);
    }

    /**
     * Metodo para retornar todos las marcas
     *
     * @return Lista de Marcas
     */
    @GetMapping("modelos/marcas")
    public List<Marca> indexMarcas() {  //Peticion de tipo get
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
    public Coche getCocheById(@PathVariable int id) {  //Peticion de tipo get
        log.info("Buscando coche con id "+id);
        return cocheService.findCocheById(id);
    }
    /**
     * Metodo para obtener la foto del modelo
     *
     * @param namePhoto
     * @return
     */
    @GetMapping("modelos/imagen/{namePhoto:.+}")
    // :.+ Indica que la foto tiene un nombre y una extension que puede ser cualquiera
    public ResponseEntity<Resource> showPhoto(@PathVariable String namePhoto) {
        Resource resource = null;
        log.info("Buscando imagen de modelo "+namePhoto);
        HttpHeaders headers = new HttpHeaders();
        try {
            resource = uploadFileService.loadModelo(namePhoto);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }

    @PostMapping("modelos/precios")
    public HashMap<String, String> preciosModelos(@RequestBody List<Integer> idsModelos) {  //Peticion de tipo get
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
    public ResponseEntity<?> getModeloById(@PathVariable int id) {  //Peticion de tipo get
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

    //TODO este metodo no se usa
    /**
     * Metodo para retornar una pagina con un numero de coches
     *
     * @return Pagina de coches
     */
    @GetMapping("coches/page/{page}")
    public Page<Coche> findCochePage(@PathVariable Integer page) {  //Peticion de tipo get
        log.info("Buscando la pagina "+page+" de coches con "+elementsForPage+" elementos");
        return cocheService.findAllCoches(PageRequest.of(page, elementsForPage));
    }

    /**
     * Metodo para retornar una pagina con un numero de modelos
     *
     * @return Pagina de modelos
     */
    @GetMapping("modelos/page/{page}")
    public Page<Modelo> findModelosPage(@PathVariable Integer page) {  //Peticion de tipo get
        log.info("Buscando la pagina "+page+" de modelos con "+elementsForPage+" elementos");
        return cocheService.findAllModelos(PageRequest.of(page, elementsForPage));
    }

    /**
     * Metodo para retornar una pagina con un numero de modelos
     *
     * @return Pagina de modelos por marcas
     */
    @GetMapping("modelospormarca/idmarca/{idmarca}/page/{page}")
    public Page<Modelo> findModelosPorMarcasPage(@PathVariable Integer idmarca, @PathVariable Integer page) {  //Peticion de tipo get
        log.info("Buscando la pagina "+page+" de modelos con "+elementsForPage+" elementos para la marca "+idmarca);
        return cocheService.findAllModelosPorMarca(PageRequest.of(page, elementsForPage),idmarca);
    }

    //TODO este metodo no se usa
    /**
     * Metodo para retornar todos los modelos segun la marca
     *
     * @return Modelos de la marca
     */
    @GetMapping("modelospormarca/idmarca/{idmarca}")
    public List<Modelo> findModelosPorMarca(@PathVariable Integer idmarca) {  //Peticion de tipo get
        log.info("Buscando los modelos para la marca "+idmarca);
        return cocheService.findAllModelosPorMarca(idmarca);
    }

    /**
     * Metodo para actualizar el numero de elementos por pagina
     *
     * @param elementsforpage Numero de elementos por pagina
     */
    @PutMapping("coches/elementsforpage")
    public void actualizarElementosPorPagina(@RequestBody int elementsforpage){
        log.info("Actualizando el numero de elementos por pagina a "+ elementsforpage);
        this.elementsForPage = elementsforpage;
//        return "Actualizando el numero de elementos por pagina a "+ elementsforpage;
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
    @PostMapping("carrocerias")
    public List<Carroceria> findAllCarrocerias(@RequestBody Object a){
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
    public List<MotorCombustion> indexMotorCombustion() {  //Peticion de tipo get
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
    public MotorCombustion getMotorCombustionById(@PathVariable int id) {  //Peticion de tipo get
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
    public List<MotorCombustion> getAllMotorCombustionByIds(@RequestBody List<Integer> idsMotorCombustion) {  //Peticion de tipo get
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
    public List<MotorElectrico> indexMotorElectrico() {  //Peticion de tipo get
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
    public MotorElectrico getMotorElectricoById(@PathVariable int id) {  //Peticion de tipo get
        log.info("Buscando MotorCombustion con id "+id);
        return cocheService.findMotorElectricoById(id);
    }


    /**
     * Metodo para obtener la foto del logo
     *
     * @param namePhoto
     * @return
     */
    @GetMapping("img/logo/{namePhoto:.+}")
    // :.+ Indica que la foto tiene un nombre y una extension que puede ser cualquiera
    public ResponseEntity<Resource> showLogo(@PathVariable String namePhoto) {
        log.info("Buscando imagen \""+namePhoto+"\"");
        Resource resource = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            resource = subirFicheroService.load(namePhoto);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resource, HttpStatus.OK);

    }
    /**
     * Metodo para obtener la foto del propietario
     *
     * @return
     */
    @GetMapping("/img/propietario")
    public ResponseEntity<Resource> showPropietario() {
        log.info("Buscando imagen del propietario");
        Resource resource = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            resource = uploadFileService.loadPropietario();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }

    /**
     * Metodo para obtener el logo de las marca
     *
     * @return Logo de la marca
     */
    @GetMapping("img/marcaslogo/{idMarca}")
    public ResponseEntity<?> showLogos(@PathVariable int idMarca) {
        log.info("Buscando el logo de la marca "+idMarca);
        Resource resource;
        Map<String, Object> response = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        resource = cocheService.getMarcaLogo(idMarca);
        if (resource == null) {
            response.put("mensaje", "El logo de la marca ID: ".concat(Integer.toString(idMarca).concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * *****************************************************************************************************************
     *                                                 Filtros
     * *****************************************************************************************************************
     */

    @PostMapping("modelos/filtros/page/{page}")
    public Page<Modelo> filtrar(@PathVariable Integer page, @RequestBody Object filtros){
        log.info("Comenzamos a filtrar");
        log.info("----------------------------------");
        Matcher mat = Pattern.compile("\\{(.*?)}").matcher(filtros.toString());
        return this.filtrosService.filtrarModelos(PageRequest.of(page, elementsForPage), this.filtrosService.estructurarFiltros(mat));
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
