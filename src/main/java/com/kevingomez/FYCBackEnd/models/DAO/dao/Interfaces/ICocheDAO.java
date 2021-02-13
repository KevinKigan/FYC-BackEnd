package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Carroceria;
import com.kevingomez.FYCBackEnd.models.entity.Coche;
import com.kevingomez.FYCBackEnd.models.entity.Modelo;
import com.kevingomez.FYCBackEnd.models.entity.TipoMotor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface ICocheDAO extends JpaRepository<Coche, Integer> {
//    List<Coche> findAllByCarroceria(Carroceria carroceria);
    List<Coche> findAllModeloDistinctByCarroceria_IdCarroceria(int carroceria);
    List<Coche> findAllByPrecioIsGreaterThanEqualAndPrecioIsLessThanEqual(int precioMin, int precioMax);
    List<Coche> findAllByPrecioIsGreaterThanEqualAndPrecioIsLessThanEqualAndAndCarroceria_Carroceria(int precioMin, int precioMax, String carroceria);
    List<Coche> findAllByConsumo_IdConsumoIn(List<Integer> idsConsumo);
    List<Coche> findAllByTipoMotorIn(List<TipoMotor> idsTipoMotor);
    List<Coche> findByModelo_IdModelo(int idModelo);
    List<Coche> findAllByIdCocheIn(List<Integer> ids);

    @Query(value = "select c from Coche c where c.modelo.idModelo in ?1 order by c.precio asc,c.modelo.idModelo desc")
    List<Coche> findPrecio(List<Integer> idsModelos);

}
