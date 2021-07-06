package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.TipoCombustible;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ITipoCombustibleDAO extends JpaRepository<TipoCombustible, Integer> {
}
