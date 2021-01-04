package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Carroceria;
import com.kevingomez.FYCBackEnd.models.entity.Coche;
import com.kevingomez.FYCBackEnd.models.entity.Modelo;
import com.kevingomez.FYCBackEnd.models.entity.TipoMotor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ICocheDAO extends JpaRepository<Coche, Integer> {
//    List<Coche> findAllByCarroceria(Carroceria carroceria);
    List<Coche> findAllModeloDistinctByCarroceria_IdCarroceria(int carroceria);
    List<Coche> findAllByPrecioIsGreaterThanEqualAndPrecioIsLessThanEqual(int precioMin, int precioMax);
    List<Coche> findAllByConsumo_IdConsumoIn(List<Integer> idsConsumo);
    List<Coche> findAllByTipoMotorIn(List<TipoMotor> idsTipoMotor);
//    List<Coche> findAllByConsumo();
}
