package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Consumo;
import com.kevingomez.FYCBackEnd.models.entity.ConsumoElectrico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IConsumoElectricoDAO extends JpaRepository<ConsumoElectrico, Integer> {
//    List<Consumo> findAllByConsumoIsGreaterThanEqualAndPrecioIsLessThanEqual(int precioMin, int precioMax);

}
