package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coche;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICocheDAO extends JpaRepository<Coche, Integer> {
}
