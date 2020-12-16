package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Carroceria;
import com.kevingomez.FYCBackEnd.models.entity.Coche;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICocheDAO extends JpaRepository<Coche, Integer> {
//    List<Coche> findAllByCarroceria(Carroceria carroceria);
    List<Coche> findAllModeloDistinctByCarroceria_IdCarroceria(int carroceria);
}
