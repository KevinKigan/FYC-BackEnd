package com.kevingomez.FYCBackEnd.controllers;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IFiltrosService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IModelosService;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Carroceria;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Marca;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Modelo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/modelos")
public class ModelosController {
    private static final Logger log = LoggerFactory.getLogger(ModelosController.class);

    @Autowired
    IModelosService modelosService;
    @Autowired
    private IFiltrosService filtrosService;

    //TODO optimizar marcas ya k tambien se envian desde marcas linkshared
    /**
     * Metodo para retornar todos las marcas
     *
     * @return Lista de Marcas
     */
    @GetMapping("marcas")
    public List<Marca> indexMarcas() {
        log.info("Buscando todas las marcas");
        return modelosService.findAllMarcas();
    }

    /**
     * Metodo para guardar una marca
     * @param marca maraca a guardar
     * @return Marca guardada
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("save_marca")
    public ResponseEntity<?> saveMarca(@RequestBody Marca marca) {
        Map<String, Object> response = new HashMap<>();
        if(marca.getMarcaCoche()!=null && !marca.getMarcaCoche().trim().equals("")) {
            log.info("Guardando marca actualizada");
            modelosService.saveMarca(marca);
            response.put("marca", marca);
            response.put("message", "Marca guardada correctamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            log.error("La marca no se ha podido guardar. Datos invalidos");
            response.put("error", "La marca no se ha podido guardar. Datos invalidos.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Metodo para guardar una marca
     * @param idsModelos Lista de los ids de los modelos
     * @return Lista de Marcas
     */
    @PostMapping("carrocerias_por_modelo")
    public ResponseEntity<?> carroceriasPorModelo(@RequestBody List<Integer> idsModelos) {
        Map<String, Object> response = new HashMap<>();
        HashMap<Integer, String> map = this.modelosService.findAllCarroceriasPorModelo(idsModelos);
//        if(marca.getMarcaCoche()!=null && !marca.getMarcaCoche().trim().equals("")) {
//            log.info("Guardando marca actualizada");
//            modelosService.saveMarca(marca);
//            response.put("marca", marca);
//            response.put("message", "Marca guardada correctamente.");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }else{
//            log.error("La marca no se ha podido guardar. Datos invalidos");
//            response.put("error", "La marca no se ha podido guardar. Datos invalidos.");
        response.put("carrocerias",map);
            return new ResponseEntity<>(response, HttpStatus.OK);
//        }
    }



    /**
     * Metodo para buscar todas las carrocerias
     *
     * @return Lista con todas las carrocerias
     */
    @GetMapping("carrocerias")
    public List<Carroceria> findAllCarrocerias(){
        log.info("Buscando todas las carrocerias");
        return this.modelosService.findAllCarrocerias();
    }

    /**
     * Metodo para retornar el modelo segun el id especificado
     *
     * @param id ID del modelo
     * @return Modelo con el id espeficicado
     */
    @GetMapping("{id}")
    public ResponseEntity<?> getModeloById(@PathVariable int id) {
        log.info("Buscando modelo con id "+id);
        Modelo modelo;
        Map<String, Object> response = new HashMap<>();
        try {
            modelo = modelosService.findModeloById(id);
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
     * @param page Pagina
     * @param pageSize Tamaño de la pagina
     * @return Pagina de modelos
     */
    @GetMapping("{pageSize}/page/{page}")
    public Page<Modelo> findModelosPage(@PathVariable Integer pageSize, @PathVariable Integer page) {
        log.info("Buscando la pagina "+page+" de modelos con "+pageSize+" elementos");
        return modelosService.findAllModelos(PageRequest.of(page, pageSize));
    }

    /**
     * Metodo para retornar una pagina con un numero de modelos
     * @param marca Marca del modelo
     * @param modelo_str Modelo
     * @return Pagina de modelos
     */
    @GetMapping("")
    public Modelo findModelosPage(@RequestParam(value = "marca_str") String marca, @RequestParam(value = "modelo_str") String modelo_str) {
        log.info("Buscando modelo con nombre "+modelo_str+" de marca con marca"+marca);
        Modelo m = modelosService.findByModeloAndMarca_MarcaCoche(marca, modelo_str);
        return m;
    }

    /**
     * Metodo para retornar una pagina con un numero de modelos
     * @param page Pagina
     * @param pageSize Tamaño de la pagina
     * @param idmarca Id de la marca
     * @return Pagina de modelos por marcas
     */
    @GetMapping("{pageSize}/idmarca/{idmarca}/page/{page}")
    public Page<Modelo> findModelosPorMarcasPage(@PathVariable Integer pageSize, @PathVariable Integer idmarca, @PathVariable Integer page) {
        log.info("Buscando la pagina "+page+" de modelos con "+pageSize+" elementos para la marca "+idmarca);
        return modelosService.findAllModelosPorMarca(PageRequest.of(page, pageSize),idmarca);
    }

    /**
     * Metodo para retornar todos los modelos segun la marca
     * @param idmarca Id de la marca
     * @return Modelos de la marca
     */
    @GetMapping("idmarca/{idmarca}")
    public List<Modelo> findModelosPorMarca(@PathVariable Integer idmarca) {
        log.info("Buscando los modelos para la marca "+idmarca);
        return modelosService.findAllModelosPorMarca(idmarca);
    }

    /*
     * *****************************************************************************************************************
     *                                                 Filtros
     * *****************************************************************************************************************
     */
    @PostMapping("{pageSize}/filtros/page/{page}")
    public Page<Modelo> filtrar(@PathVariable Integer page, @PathVariable Integer pageSize, @RequestBody Object filtros){
        log.info("Comenzamos a filtrar");
        log.info("----------------------------------");
        Matcher mat = Pattern.compile("\\{(.*?)}").matcher(filtros.toString());
        return this.filtrosService.filtrarModelos(PageRequest.of(page, pageSize), this.filtrosService.estructurarFiltros(mat));
    }

    @GetMapping("imagen")
    public HashMap<String,String> getChartSemejantesByIdCoche(@RequestBody HashMap<String, String> map){
        return this.modelosService.findImagen(map.get("modelo"),map.get("marca"));
    }
}
