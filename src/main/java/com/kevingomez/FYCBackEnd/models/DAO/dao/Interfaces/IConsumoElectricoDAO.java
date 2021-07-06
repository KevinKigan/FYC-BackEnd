package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.ConsumoElectrico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConsumoElectricoDAO extends JpaRepository<ConsumoElectrico, Integer> {
//    List<Consumo> findAllByConsumoIsGreaterThanEqualAndPrecioIsLessThanEqual(int precioMin, int precioMax);

}
