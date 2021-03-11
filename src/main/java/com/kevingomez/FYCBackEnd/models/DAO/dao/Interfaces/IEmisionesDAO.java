package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.Emisiones;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IEmisionesDAO extends JpaRepository<Emisiones, Integer> {
    List<Emisiones> findAllByCO2IsLessThanEqual(int CO2);
    List<Emisiones> findAllByTipoEmisones_IdTipoEmisiones(int id);

}
