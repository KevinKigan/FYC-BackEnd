package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMarcaDAO extends JpaRepository<Marca, Integer> {

}
