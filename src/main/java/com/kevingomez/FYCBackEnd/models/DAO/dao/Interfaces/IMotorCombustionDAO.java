package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.MotorCombustion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface IMotorCombustionDAO extends JpaRepository<MotorCombustion, Integer> {
    List<MotorCombustion> findAllByEmisiones_IdEmisionesIn(ArrayList<Integer> idsEmisiones);
    List<MotorCombustion> findAllByCilindros(int cilindros);
    List<MotorCombustion> findAllByCilindrada(double cilindrada);
    List<MotorCombustion> findAllByHpIsGreaterThanEqualAndHpIsLessThanEqual(int hpMin, int hpMax);
    List<MotorCombustion> findAllByCilindradaIsGreaterThanEqualAndCilindradaIsLessThanEqual(double cilindradaMin, double cilindradaMax);
    List<MotorCombustion> findByIdMotorCombustionIn(List<Integer> idsMotorCombustion);


}
