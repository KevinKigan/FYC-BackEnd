package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Marca;
import com.kevingomez.FYCBackEnd.models.entity.Modelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IModeloDAO extends JpaRepository<Modelo, Integer> {
    Page<Modelo> findAllByMarca_IdMarca(Pageable pageable,int idMarca);
//    List<Modelo> find

}
