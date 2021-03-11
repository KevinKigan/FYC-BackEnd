package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Coches.ConsumoAlternativo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IConsumoAlternativoDAO extends JpaRepository<ConsumoAlternativo, Integer> {
    List<ConsumoAlternativo> findAllByAutopistaIsLessThanEqual(double consumoAutopista);
    List<ConsumoAlternativo> findAllByCiudadIsLessThanEqual(double consumoCiudad);
    List<ConsumoAlternativo> findAllByCombinadoIsLessThanEqual(double consumoCombinado);
}
