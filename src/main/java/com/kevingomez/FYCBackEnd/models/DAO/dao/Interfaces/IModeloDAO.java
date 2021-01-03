package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Modelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface IModeloDAO extends JpaRepository<Modelo, Integer> {
    Page<Modelo> findAllByMarca_IdMarca(Pageable pageable,int idMarca);
//    List<Modelo> findAllByIdModelo(List<Integer> idModelo);
    List<Modelo> findAllByMarca_IdMarca(int idMarca);

     List<Modelo> findAllByIdModeloIn(List<Integer> modelos);
//    Page<Modelo> findAllByMarca_IdMarca(Pageable page,Iterable<Integer> idMarca);
//    List<Modelo> findAllById(Pageable pageable, Iterable<Integer> var1);
}
