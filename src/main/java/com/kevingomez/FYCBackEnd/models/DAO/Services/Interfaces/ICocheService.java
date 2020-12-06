package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coche;
import com.kevingomez.FYCBackEnd.models.entity.MotorCombustion;
import com.kevingomez.FYCBackEnd.models.entity.MotorElectrico;

import java.util.List;

public interface ICocheService {
    List<Coche> findAllCoches();
    List<MotorCombustion> findAllMotorCombustion();
    List<MotorElectrico> findAllMotorElectrico();

}
