package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coche;
import com.kevingomez.FYCBackEnd.models.entity.MotorConbustion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICocheDAO extends JpaRepository<Coche, Integer> {
}
