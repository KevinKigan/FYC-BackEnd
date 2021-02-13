package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.*;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ICocheService {
    List<Coche> findAllCoches();
    List<MotorCombustion> findAllMotorCombustion();
    List<MotorElectrico> findAllMotorElectrico();
    Coche findCocheById(int id);
    Modelo findModeloById(int id);
    Page<Coche> findAllCoches(Pageable pageable);
    MotorCombustion findMotorCombustionById(int id);
    MotorElectrico findMotorElectricoById(int id);
    Page<Modelo> findAllModelos(Pageable pageable);
    HashMap<String, String> findAllPreciosList(List<Integer> ids);
    Page<Modelo> findAllModelosPorMarca(Pageable pageable, int idMarca);
    List<Modelo> findAllModelosPorMarca(int idMarca);
    List<Marca> findAllMarcas();
    List<Consumo> findAllConsumosById(List<Integer>idsCoches);
    HashMap<String, String> findChartId(int idCoche);
    HashMap<String, String> findChartSemejantesId(int idCoche);
    List<MotorCombustion> getAllMotorCombustionByIds(List<Integer> idsMotorCombustion);
    List<Coche>findAllCocheByIdModelo(int idModelo);
    ArrayList generateDataVolumenCarroceria(int idCarroceria);
    Resource getMarcaLogo(int idMarca);
    List<Carroceria> findAllCarrocerias();

    HashMap<String,String> findImagen(String modelo, String marca);
}
