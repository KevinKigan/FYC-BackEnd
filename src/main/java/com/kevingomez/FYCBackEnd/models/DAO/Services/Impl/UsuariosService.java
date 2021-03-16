package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IUsuariosService;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IUsuarioDAO;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Verificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class UsuariosService implements IUsuariosService {

    @Autowired
    private IUsuarioDAO usuarioDAO;

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
    @Transactional(readOnly = true)
    public Page<Usuario> findAllPageable(Pageable pageable){
        return usuarioDAO.findAll(pageable);
    }

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
}
