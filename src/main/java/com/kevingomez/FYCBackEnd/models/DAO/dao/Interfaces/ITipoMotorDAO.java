package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.MotorCombustion;
import com.kevingomez.FYCBackEnd.models.entity.TipoMotor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ITipoMotorDAO extends JpaRepository<TipoMotor, Integer> {
    List<TipoMotor> findAllByMotorCombustionIn(List<MotorCombustion> idsMotorCombustion);
}
