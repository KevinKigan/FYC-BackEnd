package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.MotorConbustion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMotorCombustionDAO extends JpaRepository<MotorConbustion, Integer> {
}
