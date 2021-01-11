package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.*;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface ICocheService {
    List<Coche> findAllCoches();
    List<MotorCombustion> findAllMotorCombustion();
    List<MotorElectrico> findAllMotorElectrico();
    Coche findCocheById(int id);
    Page<Coche> findAllCoches(Pageable pageable);
    MotorCombustion findMotorCombustionById(int id);
    MotorElectrico findMotorElectricoById(int id);
    Page<Modelo> findAllModelos(Pageable pageable);
    Page<Modelo> findAllModelosPorMarca(Pageable pageable, int idMarca);
    List<Modelo> findAllModelosPorMarca(int idMarca);
    List<Marca> findAllMarcas();
    ArrayList generateDataVolumenCarroceria(int idCarroceria);
    Resource getMarcaLogo(int idMarca);
    List<Carroceria> findAllCarrocerias();
}
