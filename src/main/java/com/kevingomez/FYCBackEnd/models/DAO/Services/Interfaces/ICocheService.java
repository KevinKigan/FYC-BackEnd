package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.*;

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

    List<TipoMotor> findAllTipoMotorByIds(List<Integer> idsTiposMotor);
    TipoMotor findTipoMotorById(int id);

    Consumo findConsumoById(Integer id);

    Emisiones getEmisionesById(int id);

    List<TipoCombustible> findAllTipoCombustible();

    List<TipoEmisiones> findAllNormativasConsumos();

    Coche save(Coche coche);

    MotorCombustion saveMotorCombustion(MotorCombustion motorCombustion);

    Consumo saveConsumo(Consumo consumo);

    Volumen findVolumenById(int id);

    MotorElectrico saveMotorElectrico(MotorElectrico motorElectrico);

    Volumen saveVolumen(Volumen volumen);
}
