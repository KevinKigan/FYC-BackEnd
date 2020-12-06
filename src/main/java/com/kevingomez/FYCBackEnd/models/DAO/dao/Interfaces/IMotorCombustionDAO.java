package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.MotorCombustion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMotorCombustionDAO extends JpaRepository<MotorCombustion, Integer> {
}
