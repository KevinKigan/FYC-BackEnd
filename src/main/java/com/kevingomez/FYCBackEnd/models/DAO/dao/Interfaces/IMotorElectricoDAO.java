package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.MotorElectrico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMotorElectricoDAO extends JpaRepository<MotorElectrico, Integer> {
}
