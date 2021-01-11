package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Sobrealimentacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISobrealimentacionDAO extends JpaRepository<Sobrealimentacion, Integer> {
    List<Sobrealimentacion> findAllByTurbo(boolean turbo);
    List<Sobrealimentacion> findAllBySupercargador(boolean supercargador);
    List<Sobrealimentacion> findAllByTurboAndSupercargador(boolean turbo, boolean supercargador);

}
