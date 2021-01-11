package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMarcaDAO extends JpaRepository<Marca, Integer> {

}
