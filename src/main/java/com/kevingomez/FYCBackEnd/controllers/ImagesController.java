package com.kevingomez.FYCBackEnd.controllers;

import com.dropbox.core.DbxException;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IFicherosService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IModelosService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IUsuariosService;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IMarcaDAO;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Marca;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.*;


@RestController
@RequestMapping("/api/img")
public class ImagesController {

    private static final Logger log = LoggerFactory.getLogger(ImagesController.class);

    @Autowired
    private IFicherosService ficherosService;
    @Autowired
    private IUsuariosService usuariosService;
    @Autowired
    private IModelosService modelosService;


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
        log.info("Buscando el logo de la marca " + idMarca);
        Map<String, Object> response = new HashMap<>();
        HashMap<Integer, String> urls;
        if (idMarca == -1) { // Se piden todas las marcas
            urls = ficherosService.getURLMarcaLogo(idMarca, true);
        } else {           // Se pide una sola marca
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

    @Secured("ROLE_USER")
    @PostMapping("/upload/{area}")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("id") int id, @PathVariable("area") String area) {
        Map<String, Object> response = new HashMap<>();
        if(area.equals("users")) {
            Usuario user = usuariosService.findById(id);
            if (user != null) {
                if (!file.isEmpty()) {
                    String filename = user.getUsername().concat("." + Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1]);
                    String responseUpload = ficherosService.uploadFile("userImage", file, filename);
                    if (responseUpload.contains("correctamente")) {
                        user.setImage(filename);
                        // Actualizamos la url de la nueva imagen del usuario
                        ficherosService.setURLUsuario(user);
                        usuariosService.save(user);
                        response.put("user", user);
                        response.put("message", responseUpload);
                        return new ResponseEntity<>(response, HttpStatus.CREATED);
                    } else {
                        response.put("error", responseUpload);
                        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    response.put("error", "Se ha enviado un fichero vacio.");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            } else {
                response.put("error", "No se ha encontrado al usuario.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }else if(area.equals("marcas")) {
            Marca marca = modelosService.findMarcaById(id);
            if (marca != null) {
                if (!file.isEmpty()) {
                    String filename = marca.getMarcaCoche().concat("." + Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1]);
                    String responseUpload = ficherosService.uploadFile("marcas", file, filename);
                    if (responseUpload.contains("correctamente")) {
                        // Actualizamos la url de la nueva imagen del usuario
                        ficherosService.setURLMarca(marca);
                        response.put("marca", marca);
                        response.put("message", responseUpload);
                        return new ResponseEntity<>(response, HttpStatus.CREATED);
                    } else {
                        response.put("error", responseUpload);
                        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    response.put("error", "Se ha enviado un fichero vacio.");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            } else {
                response.put("error", "No se ha encontrado la marca.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }
        return null;
    }

    @Secured("ROLE_USER")
    @GetMapping("/getUserImage/{id}")
    public ResponseEntity<?> getMyImage(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        HashMap<Integer, String> urls = ficherosService.getURLUsuario(Collections.singletonList(id));
        response.put("list",urls);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
