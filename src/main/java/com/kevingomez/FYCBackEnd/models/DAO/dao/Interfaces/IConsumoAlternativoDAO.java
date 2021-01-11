package com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Consumo;
import com.kevingomez.FYCBackEnd.models.entity.ConsumoAlternativo;
import com.kevingomez.FYCBackEnd.models.entity.ConsumoNormal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IConsumoAlternativoDAO extends JpaRepository<ConsumoAlternativo, Integer> {
    List<ConsumoAlternativo> findAllByAutopistaIsLessThanEqual(double consumoAutopista);
    List<ConsumoAlternativo> findAllByCiudadIsLessThanEqual(double consumoCiudad);
    List<ConsumoAlternativo> findAllByCombinadoIsLessThanEqual(double consumoCombinado);
}
