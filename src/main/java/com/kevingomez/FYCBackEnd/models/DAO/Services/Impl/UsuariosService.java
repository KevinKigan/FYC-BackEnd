package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IUsuariosService;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IUsuarioDAO;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuariosService implements IUsuariosService {

    @Autowired
    private IUsuarioDAO usuarioDAO;

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
    public Page<Usuario> findAllPageable(Pageable pageable){
        return usuarioDAO.findAll(pageable);
    }
}
