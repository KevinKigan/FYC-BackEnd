package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDAO extends JpaRepository<Usuario,Integer> {
    Usuario findByUsername(String username);
    Usuario findByEmail(String email);
}
