package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IUsuariosService;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IRolDAO;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IUsuarioDAO;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Rol;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Verificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuariosService implements IUsuariosService, UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UsuariosService.class);

    @Autowired
    private IUsuarioDAO usuarioDAO;
    @Autowired
    private IRolDAO rolDAO;

    private HashMap<Integer, Verificacion> verificacionEnProceso= new HashMap<>();

    @Override
    @Transactional
    public Usuario save(Usuario user) {
        return usuarioDAO.save(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        usuarioDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findById(int id) {
        return usuarioDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioDAO.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByEmail(String email) {
        return usuarioDAO.findByEmail(email);
    }

    @Override
    public HashMap<String, Object> setRoles(ArrayList<String> roles, int id) {
        HashMap<String, Object> map = new HashMap<>();
        Usuario user = usuarioDAO.findById(id).orElse(null);
        if(user!=null) {
            List<Rol> actualRoles = user.getRoles();
            List<Rol> allRoles = rolDAO.findAll();
            for (Rol rol :allRoles) {
                if(roles.contains(rol.getRolName())){
                    // Borramos el rol del usuario
                    if(user.getRoles().contains(rol)){
                        for (Rol actualRol:actualRoles) {
                            if(actualRol.getRolName().equals(rol.getRolName())){
                                actualRoles.remove(actualRol);
                            }
                            break;
                        }
                    }
                    // Añadimos el rol al usuario
                    else{
                        actualRoles.add(rol);
                    }
                }
            }
            user.setRoles(actualRoles);
            usuarioDAO.save(user);
            log.info("Roles actualizados correctamente.");
            map.put("message", "Roles actualizados correctamente.");
            map.put("roles", user.getRoles());
            return map;
        }
        map.put("error", "Usuario no encontrado.");
        return map;
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAllPageable(Pageable pageable){
        return usuarioDAO.findAll(pageable);
    }

    /**
     * Metodo para verificar al nuevo usuario con el cogido de verificacion
     * que se le ha enviado por email
     *
     * @param id
     * @param code
     * @return
     */
    @Override
    public String comprobarVerificado(int id, String code){
        Verificacion verify = this.verificacionEnProceso.get(id);
        if(verify == null){
            return "No hay usuario con ese identificador.";
        }else{
            long verifyTime = verify.getTime();
            long actualTime = System.currentTimeMillis();
            long sub = actualTime - verifyTime;
            if(sub<=240000) { // 4 Minutos
                if(code.equals(verify.getCodigo())){
                    Usuario user = this.usuarioDAO.findById(id).orElse(null);
                    if(user!=null){
                        user.setVerified(true);
                        this.usuarioDAO.save(user);
                        this.verificacionEnProceso.remove(id);
                    }
                    return "Verificado.";
                }else{
                    return "Código incorrecto.";
                }
            }else{
                return "El código ha expirado.";
            }
        }
    }


    public void addVerificacionEnProceso(int id, Verificacion verificacion) {
        this.verificacionEnProceso.put(id, verificacion);
    }

    /**
     * Metodo para implementar el metodo login mediante spring security
     * @param username Nombre de usuario con el que se va a hacer login y
     *                 recuperar sus roles
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByUsername(username);
        if(usuario==null) {
            log.error("Error al iniciar sesión: No existe el usuario.");
            throw new UsernameNotFoundException("Error al iniciar sesión: No existe el usuario.");
        }
        // Se convierte una lista de roles de tipo Rol a una lista de GrantedAuthority que contiene esos roles
        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRolName()))
                .peek(authority -> log.info("Rol: "+authority.getAuthority()))
                .collect(Collectors.toList());
        return new User(usuario.getUsername(),usuario.getPassword(),usuario.getEnabled(),
                true,true,true, authorities);
    }
}
