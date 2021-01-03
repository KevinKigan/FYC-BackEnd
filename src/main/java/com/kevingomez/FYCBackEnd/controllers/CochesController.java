package com.kevingomez.FYCBackEnd.controllers;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IFiltrosService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ISubirFicheroService;
import com.kevingomez.FYCBackEnd.models.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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

    /**
     * *****************************************************************************************************************
     *                                                      Coches
     * *****************************************************************************************************************
     */

    /**
     * Metodo para retornar todos los coches
     *
     * @return Lista de Coches
     */
    @GetMapping("coches")
    public List<Coche> indexCoches() {  //Peticion de tipo get
        log.info("Buscando todos los coches");
        return cocheService.findAllCoches();
    }

    /**
     * Metodo para retornar todos las marcas
     *
     * @return Lista de Marcas
     */
    @GetMapping("coches/marcas")
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
    @GetMapping("coches/modelos/page/{page}")
    public Page<Modelo> findModelosPage(@PathVariable Integer page) {  //Peticion de tipo get
        log.info("Buscando la pagina "+page+" de modelos con "+elementsForPage+" elementos");
        return cocheService.findAllModelos(PageRequest.of(page, elementsForPage));
    }

    /**
     * Metodo para retornar una pagina con un numero de modelos
     *
     * @return Pagina de modelos por marcas
     */
    @GetMapping("coches/modelospormarca/idmarca/{idmarca}/page/{page}")
    public Page<Modelo> findModelosPorMarcasPage(@PathVariable Integer idmarca, @PathVariable Integer page) {  //Peticion de tipo get
        log.info("Buscando la pagina "+page+" de modelos con "+elementsForPage+" elementos para la marca "+idmarca);
        return cocheService.findAllModelosPorMarca(PageRequest.of(page, elementsForPage),idmarca);
    }

    /**
     * Metodo para retornar todos los modelos segun la marca
     *
     * @return Modelos de la marca
     */
    @GetMapping("coches/modelospormarca/idmarca/{idmarca}")
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
     * Metodo para obtener la foto del coche
     *
     * @param namePhoto
     * @return
     */
    @GetMapping("img/logo/{namePhoto:.+}")
    // :.+ Indica que la foto tiene un nombre y una extension que puede ser cualquiera
    public ResponseEntity<Resource> showPhoto(@PathVariable String namePhoto) {
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

    @PostMapping("coches/filtros/page/{page}")
    public List<Modelo> filtrar(@PathVariable Integer page, @RequestBody Object filtros){
        log.info("Estamos filtrando");
        Matcher mat = Pattern.compile("\\{(.*?)}").matcher(filtros.toString());
        return this.cocheService.findAllModelosFiltrados(PageRequest.of(page, elementsForPage), this.filtrosService.estructurarFiltros(mat));
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
        return cocheService.generateDataVolumenCarroceria(idCarroceria);
    }
}
