package com.kevingomez.FYCBackEnd.controllers;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Impl.CocheService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.entity.Coche;
import com.kevingomez.FYCBackEnd.models.entity.MotorCombustion;
import com.kevingomez.FYCBackEnd.models.entity.MotorElectrico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class CochesController {

    @Autowired
    private ICocheService cocheService;

    /**
     * Metodo para retornar todos los coches
     *
     * @return Lista de clientes
     */
    @GetMapping("/coches")
    public List<Coche> indexCoches() {  //Peticion de tipo get
        return cocheService.findAllCoches();
    }

    @GetMapping("/combustion")
    public List<MotorCombustion> indexMotorCombustion() {  //Peticion de tipo get
        return cocheService.findAllMotorCombustion();
    }

    @GetMapping("/electrico")
    public List<MotorElectrico> indexMotorElectrico() {  //Peticion de tipo get
        return cocheService.findAllMotorElectrico();
    }


}
