package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.TipoEmisiones;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ITipoEmisionesDAO extends JpaRepository<TipoEmisiones, Integer> {
    TipoEmisiones findByTipoEmisiones(String tipoEmisiones);
}
