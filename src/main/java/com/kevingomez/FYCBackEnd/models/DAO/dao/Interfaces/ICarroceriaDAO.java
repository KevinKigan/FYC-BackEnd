package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Carroceria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarroceriaDAO extends JpaRepository<Carroceria,Integer> {
    Carroceria getCarroceriaByCarroceria(String carroceria);
}
