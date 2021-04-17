package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;


import com.kevingomez.FYCBackEnd.models.entity.Coches.Carroceria;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Marca;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Modelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface IModelosService {
    List<Marca> findAllMarcas();
    List<Carroceria> findAllCarrocerias();
    Modelo findModeloById(int id);
    Page<Modelo> findAllModelos(Pageable pageable);
    Marca saveMarca(Marca marca);
    Page<Modelo> findAllModelosPorMarca(Pageable pageable, int idMarca);
    List<Modelo> findAllModelosPorMarca(int idMarca);
    HashMap<String,String> findImagen(String modelo, String marca);
}
