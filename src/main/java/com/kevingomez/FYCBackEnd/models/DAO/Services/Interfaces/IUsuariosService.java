package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IUsuariosService {
    Page<Usuario> findAllPageable(Pageable pageable);
    Usuario save(Usuario user);
    void delete(int id);
    Usuario findById(int id);
    String comprobarVerificado(int id, String code);
    Usuario findByUsername(String username);
    Usuario findByEmail(String email);
    HashMap<String, Object> setRoles(ArrayList<String> roles, int id);
    List<Usuario> findAll();
}
