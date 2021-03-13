package com.kevingomez.FYCBackEnd.controllers;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IUsuariosService;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})

//@CrossOrigin(origins = {"https://findyourcarapp-96860.web.app"})
@RestController
@RequestMapping("/api/user")
public class UsuariosController {

    private static final Logger log = LoggerFactory.getLogger(UsuariosController.class);

    @Autowired
    private IUsuariosService usuariosService;

    /**
     * Metodo para retornar todos los usuarios
     * @return Pagina con los usuarios
     */
    @GetMapping("/index/{pageSize}/page/{page}")
    public Page<Usuario> index(@PathVariable Integer pageSize, @PathVariable Integer page){
        log.info("Buscamos usuarios. Página "+page+" con "+pageSize+" elementos");
        return usuariosService.findAllPageable(PageRequest.of(page, pageSize));
    }

    /**
     * metodo para obtener un usuario mediante id
     * @param id Identificador del usuario
     * @return Usuario encontrado
     */
    @GetMapping("/{id}")
    public Usuario show(@PathVariable int id){
        return usuariosService.findById(id);
    }

    /**
     * Metodo para borrar un usuario mediente id
     * @param id Identificador del usuario
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        usuariosService.delete(id);
    }

    /**
     * Metodo para crear un usuario
     * @param usuario Usuario a crear
     * @return Usuario creado
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create")
    public Usuario create(@RequestBody Usuario usuario){
        return usuariosService.save(usuario);
    }

    /**
     * Metodo para actualizar un usuario
     * @param usuario Usuario a modificar
     * @param id Identificador del usuario
     * @return Usuario modificado
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{id}")
    public Usuario update(@RequestBody Usuario usuario,@PathVariable int id){
        Usuario usuarioBBDD = usuariosService.findById(id);
        usuarioBBDD.setImage(usuario.getImage());
        usuarioBBDD.setEnabled(usuario.getEnabled());
        usuarioBBDD.setUsername(usuario.getUsername());
        usuarioBBDD.setVerified(usuario.getVerified());
        usuarioBBDD.setEmail(usuario.getEmail());
//        usuarioBBDD.setPassword(usuario.getPassword());
        return usuariosService.save(usuarioBBDD);
    }



}
