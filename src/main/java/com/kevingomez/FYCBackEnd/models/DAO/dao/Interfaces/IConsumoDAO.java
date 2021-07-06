package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.Consumo;
import com.kevingomez.FYCBackEnd.models.entity.Coches.ConsumoNormal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IConsumoDAO extends JpaRepository<Consumo, Integer> {
//    List<Consumo> findAllByConsumoIsGreaterThanEqualAndPrecioIsLessThanEqual(int precioMin, int precioMax);
    List<Consumo> findAllByIdConsumoNormalIn(List<ConsumoNormal> idConsumoNormal);
    List<Consumo> findAllByIdConsumoIn(List<Integer> idsConsumo);

//    List<Consumo> findAllByIdConsumoNormalIn(List<Integer> consumosNormales);
//    List<Consumo> findAllByIdConsumoNormalInAndIdConsumoAlternativo(List<Integer> consumosNormales, List<Integer> consumosAlt);
}
