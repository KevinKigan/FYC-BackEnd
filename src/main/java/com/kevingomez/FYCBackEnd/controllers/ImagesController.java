package com.kevingomez.FYCBackEnd.controllers;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IFicherosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/img")
public class ImagesController {

    private static final Logger log = LoggerFactory.getLogger(ImagesController.class);

    @Autowired
    private IFicherosService ficherosService;


    /**
     * Metodo para obtener la foto del propietario
     *
     * @return
     */
    @GetMapping("propietario")
    public ResponseEntity<Resource> showPropietario() {
        log.info("Buscando imagen del propietario");
        Resource resource = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            resource = ficherosService.loadPropietario();
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
    @GetMapping("marcaslogo/{idMarca}")
    public ResponseEntity<?> showLogosMarcas(@PathVariable int idMarca) {
        log.info("Buscando el logo de la marca "+idMarca);
        Map<String, Object> response = new HashMap<>();
        HashMap<Integer, String> urls;
        if(idMarca==-1){ // Se piden todas las marcas
            urls = ficherosService.getURLMarcaLogo(idMarca, true);
        }else{           // Se pide una sola marca
            urls = ficherosService.getURLMarcaLogo(idMarca, false);
        }
        if (urls.size() == 0) {
            response.put("mensaje", "El logo de la marca ID: ".concat(Integer.toString(idMarca).concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(urls, HttpStatus.OK);
    }

    /**
     * Metodo para obtener el logo de los modelos
     *
     * @return Logo del modelo
     */
    @PostMapping("modeloslogo")
    public ResponseEntity<?> showLogosModelos(@RequestBody List<Integer> idsModelos) {
        log.info("Buscando logos de modelos ");
        HashMap<Integer, String> urls;
        urls = ficherosService.getURLModelosLogo(idsModelos);
        return new ResponseEntity<>(urls, HttpStatus.OK);
    }
}
