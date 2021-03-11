package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.Volumen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVolumenDAO extends JpaRepository<Volumen, Integer> {
}
