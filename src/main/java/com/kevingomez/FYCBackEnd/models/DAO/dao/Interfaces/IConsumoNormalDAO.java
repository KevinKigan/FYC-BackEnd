package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Consumo;
import com.kevingomez.FYCBackEnd.models.entity.ConsumoNormal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IConsumoNormalDAO extends JpaRepository<ConsumoNormal, Integer> {
    List<ConsumoNormal> findAllByAutopistaIsLessThanEqual(double consumoAutopista);
    List<ConsumoNormal> findAllByCiudadIsLessThanEqual(double consumoCiudad);
    List<ConsumoNormal> findAllByCombinadoIsLessThanEqual(double consumoCombinado);
}
