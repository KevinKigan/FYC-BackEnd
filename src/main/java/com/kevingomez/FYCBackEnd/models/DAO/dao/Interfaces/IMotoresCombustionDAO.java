package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coche;
import com.kevingomez.FYCBackEnd.models.entity.MotorCombustion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface IMotoresCombustionDAO extends JpaRepository<MotorCombustion, Integer> {
    List<MotorCombustion> findAllByEmisiones_IdEmisionesIn(ArrayList<Integer> idsEmisiones);
}
