package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.*;
import com.kevingomez.FYCBackEnd.models.filters.Filter;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.MalformedURLException;
import java.util.List;
import java.util.regex.Matcher;

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
    Coche generateDataVolumenCarroceria(int idCarroceria);
    Resource getMarcaLogo(int idMarca);
    List<Carroceria> findAllCarrocerias();
    List<Modelo> findAllModelosFiltrados(Pageable pageable, Filter filtros);
}
