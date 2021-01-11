package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Volumen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVolumenDAO extends JpaRepository<Volumen, Integer> {
}
