package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.MotorCombustion;
import com.kevingomez.FYCBackEnd.models.entity.Coches.TipoMotor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITipoMotorDAO extends JpaRepository<TipoMotor, Integer> {
    List<TipoMotor> findAllByMotorCombustionIn(List<MotorCombustion> idsMotorCombustion);
    List<TipoMotor> findAllByMotorCombustion_IdMotorCombustionIn(List<Integer> idsMotorCombustion);
}
