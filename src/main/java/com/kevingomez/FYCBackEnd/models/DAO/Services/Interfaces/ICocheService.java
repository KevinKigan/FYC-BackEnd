package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ICocheService {
    List<MotorCombustion> findAllMotorCombustion();
    List<MotorElectrico> findAllMotorElectrico();
    Coche findCocheById(int id);
    MotorCombustion findMotorCombustionById(int id);
    MotorElectrico findMotorElectricoById(int id);
    HashMap<String, String> findAllPreciosList(List<Integer> ids);
    List<Consumo> findAllConsumosById(List<Integer>idsCoches);
    HashMap<String, String> findChartId(int idCoche);
    HashMap<String, String> findChartSemejantesId(int idCoche);
    List<MotorCombustion> getAllMotorCombustionByIds(List<Integer> idsMotorCombustion);
    List<Coche>findAllCocheByIdModelo(int idModelo);
    ArrayList generateDataVolumenCarroceria(int idCarroceria);
}
