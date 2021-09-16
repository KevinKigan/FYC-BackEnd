package com.kevingomez.FYCBackEnd.controllers;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IEmailService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IFicherosService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IUsuariosService;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Rol;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/user")
public class UsuariosController {

    private static final Logger log = LoggerFactory.getLogger(UsuariosController.class);

    @Autowired
    private IUsuariosService usuariosService;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private IFicherosService ficherosService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/index")
    public List<Usuario> index() {
        return usuariosService.findAll();
    }

    /**
     * Metodo para retornar todos los usuarios
     * @param page Pagina
     * @param pageSize Tamaño de la pagina
     * @return Pagina con los usuarios
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/index/{pageSize}/page/{page}")
    public Page<Usuario> index(@PathVariable Integer pageSize, @PathVariable Integer page) {
        log.info("Buscamos usuarios. Página " + page + " con " + pageSize + " elementos");
        return usuariosService.findAllPageable(PageRequest.of(page, pageSize));
    }

    /**
     * metodo para obtener un usuario mediante id
     *
     * @param id Identificador del usuario
     * @return Usuario encontrado
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable int id) {
        Usuario user;
        Map<String, Object> response = new HashMap<>();
        try {
            user = usuariosService.findById(id);
        } catch (DataAccessException e) {
            response.put("error", "Error al realizar la consulta en la base de datos.");
            log.error(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user == null) {
            response.put("error", "El usuario no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);

    }


    /**
     * metodo para obtener un usuario mediante id
     * @param token token
     * @param username Nomrbe de usuario
     * @return Usuario encontrado
     */
    @Secured("ROLE_USER")
    @GetMapping("/username/{username}")
    public ResponseEntity<?> showMyUser(@PathVariable String username, @RequestHeader(name = "Authorization") String token) {
        Usuario user;
        Map<String, Object> response = new HashMap<>();
        String usernameToken;
        try {
            usernameToken = getUsername(token);
        } catch (ParseException e) {
            response.put("error", "Error al comprobar veracidad del token.");
            log.error(e.toString());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (usernameToken == null || usernameToken.equals("")) {
            response.put("error", "El nombre de usuario no se ha podido comprobar.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!usernameToken.equals(username)) {
            response.put("error", "Se ha detectado manupulación de datos. Acceso denegado!");
            log.error("Se ha detectado manupulación de datos. Acceso denegado!");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        try {
            user = usuariosService.findByUsername(username);
            user.setPassword("");
        } catch (DataAccessException e) {
            response.put("error", "Error al realizar la consulta en la base de datos.");
            log.error(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user == null) {
            response.put("error", "El usuario no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    /**
     * Metodo para comprobar la validez del token
     *
     * @param token
     * @return Nombre de usuario
     */
    private String getUsername(String token) throws ParseException {
        String payload = token.split("\\.")[1];
        payload = new String(Base64.getDecoder().decode(payload));
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(payload);
            /* La verificacion de token se realiza de manera automatica
               por lo que no es necesaria comprobarla aqui */
        return json.get("username").toString();
    }

//    /**
//     * Metodo para deshabilitar un usuario mediente id
//     *
//     * @param id Identificador del usuario
//     */
//    @Secured("ROLE_ADMIN")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @GetMapping("/disable/{id}")
//    public ResponseEntity<?> delete(@PathVariable int id) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            Usuario user = usuariosService.findById(id);
//            user.setEnabled(false);
//            usuariosService.save(user);
//        } catch (DataAccessException e) {
//            response.put("error", "Error al deshabilitar al usuario.");
//            log.error(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        response.put("message","El usuario se ha deshabilitado correctamente.");
//
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    /**
     * Metodo para crear un usuario
     *
     * @param result Result
     * @param usuario Usuario a crear
     * @return Usuario creado
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {
        //@Valid valida el usuario desde el propio body de la peticion
        Usuario user = null;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(e -> "" + e.getDefaultMessage()).collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            usuario.setEnabled(true);
            user = usuariosService.create(usuario);
            //todo
        } catch (DataAccessException e) {
            if (Objects.requireNonNull(e.getMostSpecificCause().getMessage()).contains("Duplicate entry")) {
                if (Objects.requireNonNull(e.getMostSpecificCause().getMessage()).contains("@")) {
                    response.put("error", "Error al insertar al usuario en la base de datos. Email ya existe.");
                } else {
                    response.put("error", "Error al insertar al usuario en la base de datos. Nombre de usuario ya existe.");
                }
            } else {
                response.put("error", "Error al insertar al usuario en la base de datos.");
            }
            log.error(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!emailService.sendEmailVerificate(usuario, "verifyNewUser")) {
            response.put("message", "Error al realizar el envio de verificacion al correo " + usuario.getEmail() + ".");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        response.put("message", usuario.getUsername() + " te has registrado exitosamente.");
        response.put("user", user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Metodo para actualizar un usuario
     *
     * @param result Result
     * @param usuario Usuario a modificar
     * @param id      Identificador del usuario
     * @return Usuario modificado
     */
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Usuario usuarioBBDD = usuariosService.findById(id);
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors().stream().map(e -> "" + e.getDefaultMessage()).collect(Collectors.toList());
                response.put("errors", errors);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (usuarioBBDD == null) {
                response.put("error", "No se puede editar, el usuario no existe.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Usuario usuarioActualizado;
            usuarioBBDD.setImage(usuario.getImage());
            usuarioBBDD.setEnabled(usuario.getEnabled());
            usuarioBBDD.setUsername(usuario.getUsername());
            usuarioBBDD.setVerified(usuario.getVerified());
            usuarioBBDD.setEmail(usuario.getEmail());
            usuarioBBDD.setRegistrationDate(usuario.getRegistrationDate());
            usuarioActualizado = usuariosService.save(usuarioBBDD);
            response.put("message", "El usuario " + usuario.getUsername() + " se ha actualizado correctamente.");
            response.put("user", usuarioActualizado);
        } catch (DataAccessException e) {
            if (Objects.requireNonNull(e.getMostSpecificCause().getMessage()).contains("Duplicate entry")) {
                if (Objects.requireNonNull(e.getMostSpecificCause().getMessage()).contains("@")) {
                    response.put("error", "Error al insertar al usuario en la base de datos. Email ya existe.");
                } else {
                    response.put("error", "Error al insertar al usuario en la base de datos. Nombre de usuario ya existe.");
                }
            } else {
                response.put("error", "Error al insertar al usuario en la base de datos.");
            }
            response.put("error", "Error al actualizar en la base de datos.");
            log.error(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            String mensaje = e.getCause().getCause().getMessage();
            mensaje = mensaje.split("messageTemplate='")[1];
            mensaje = mensaje.substring(0, mensaje.length() - 4);
            response.put("error", "Error al actualizar en la base de datos. " + mensaje);
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Metodo para actualizar los roles del usuario
     *
     * @param roles Lista de roles
     * @param id Id del usuario
     * @return Usuario
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/set_roles/{id}")
    public ResponseEntity<?> setRoles(@RequestBody ArrayList<String> roles, @PathVariable int id) {
        log.info("Actualizando roles del usuario " + id);
        Map<String, Object> response;
        response = usuariosService.setRoles(roles, id);
        if (response.containsKey("message")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Metodo para enviar por correo un codigo de verificacion
     *
     * @param tipo Tipo
     * @param identificador Identificador del usuario
     * @return Usuario
     */
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/send_verification_code/{identificador}/{tipo}")
    public ResponseEntity<?> sendVerificationCode(@PathVariable String identificador, @PathVariable String tipo) {
        Usuario usuario;
        Map<String, Object> response = new HashMap<>();
        try {
            int id = Integer.parseInt(identificador);
            usuario = usuariosService.findById(id);
        } catch (NumberFormatException excepcion) {

            usuario = usuariosService.findByUsername(identificador);
            if (usuario == null) {
                usuario = usuariosService.findByEmail(identificador);
            }
        }
        if (usuario != null) {
            this.emailService.sendEmailVerificate(usuario, tipo);
            response.put("user", usuario);
        } else {
            response.put("error", "No se ha encontrado el usuario.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Metodo para comporbar el codigo de verificacion enviado por correo
     *
     * @param id Id del usuario
     * @param code Codigo de verificacion
     * @return Respuesta de la verificacion
     */
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/check_verification_code/{id}/{code}")
    public ResponseEntity<?> checkVerificationCode(@PathVariable int id, @PathVariable String code) {
        Map<String, Object> response = new HashMap<>();
        String status = this.usuariosService.comprobarVerificado(id, code);
        if (status.equals("Verificado.")) {
            response.put("message", status);
        } else {
            response.put("error", status);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Metodo para cambiar la contraseña al haber sido olvidada y
     * enviar por correo un codigo de verificacion
     *
     * @param id Id del usuario
     * @return HttpRequest de respuesta del envio
     */
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/send_verification_code/new_pass/{id}")
    public ResponseEntity<?> sendVerificationCodeNewPassword(@PathVariable int id) {
        Usuario usuario = usuariosService.findById(id);
        this.emailService.sendEmailVerificate(usuario, "forgottenPassword");
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }


}
