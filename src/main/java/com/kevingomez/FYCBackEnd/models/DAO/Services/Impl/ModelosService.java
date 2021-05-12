package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IModelosService;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.ICarroceriaDAO;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.ICocheDAO;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IMarcaDAO;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IModeloDAO;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Carroceria;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Coche;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Marca;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Modelo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ModelosService implements IModelosService {
    private static final Logger log = LoggerFactory.getLogger(ModelosService.class);

    @Autowired
    private IMarcaDAO marcaDAO;
    @Autowired
    private ICarroceriaDAO carroceriaDAO;
    @Autowired
    private IModeloDAO modeloDAO;
    @Autowired
    private ICocheDAO cocheDAO;

    /**
     * Metodo para retornar todas las marcas
     *
     * @return Lista de marcas
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<Marca> findAllMarcas() {
        return marcaDAO.findAll();
    }

    /**
     * Metodo para guardar una marca
     *
     * @return Lista de marcas
     */
    @Override
    public Marca saveMarca(Marca marca) {
        return marcaDAO.save(marca);
    }



    /**
     * Metodo para buscar en bbdd todas las carrocerias
     *
     * @return Lista de carrocerias
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<Carroceria> findAllCarrocerias() {
        return carroceriaDAO.findAll();
    }

    /**
     * Metodo para retornar un modelo segun su id
     *
     * @param id Id del modelo a retornar
     * @return Modelo con id especificado
     */
    @Override
    public Modelo findModeloById(int id) {
        return modeloDAO.findById(id).orElse(null);
    }


    /**
     * Metodo para retornar una marca segun su id
     *
     * @param id Id de la marca a retornar
     * @return Marca con id especificado
     */
    @Override
    public Marca findMarcaById(int id) {
        return marcaDAO.findById(id).orElse(null);
    }

    /**
     * Metodo para retornar una pagina con modelos
     *
     * @param pageable Pagina de modelos a buscar
     * @return Pagina con los modelos
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public Page<Modelo> findAllModelos(Pageable pageable) {
        return modeloDAO.findAll(pageable);
    }


    /**
     * Metodo para retornar una pagina con modelos segun la marca especificada
     *
     * @param pageable Pagina de modelos a buscar
     * @return Pagina con los modelos
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public Page<Modelo> findAllModelosPorMarca(Pageable pageable, int idMarca) {
        // Lo retorna si lo encuentra y en caso contrario retorna null
        return modeloDAO.findAllByMarca_IdMarca(pageable, idMarca);
    }

    /**
     * Metodo para retornar todos los modelos segun la marca especificada
     *
     * @return Lista con los modelos
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<Modelo> findAllModelosPorMarca(int idMarca) {
        // Lo retorna si lo encuentra y en caso contrario retorna null
        return modeloDAO.findAllByMarca_IdMarca(idMarca);
    }

    @Override
    public HashMap<String,String> findImagen(String modelo, String marca) {
        HashMap<String,String> map = new HashMap();
        map.put("imagen",this.modeloDAO.findByModeloAndMarca_MarcaCoche(modelo, marca).getImagen());
        return map;
    }

    @Override
    public HashMap<Integer, String> findAllCarroceriasPorModelo(List<Integer> idsModelos) {
        HashMap<Integer, String> map = new HashMap<>();
        idsModelos.forEach(idModelo -> map.put(idModelo,this.cocheDAO.findTop1ByModelo_IdModelo(idModelo).getCarroceria().getCarroceria()));
        return map;
    }

}
