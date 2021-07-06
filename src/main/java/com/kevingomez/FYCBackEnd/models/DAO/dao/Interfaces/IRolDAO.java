package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolDAO extends JpaRepository<Rol,Integer> {
}
