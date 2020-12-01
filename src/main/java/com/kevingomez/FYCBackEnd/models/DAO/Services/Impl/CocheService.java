package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.ICocheDAO;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IMotorCombustionDAO;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IMotorElectricoDAO;
import com.kevingomez.FYCBackEnd.models.entity.Coche;
import com.kevingomez.FYCBackEnd.models.entity.MotorConbustion;
import com.kevingomez.FYCBackEnd.models.entity.MotorElectrico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CocheService implements ICocheService {
    @Autowired
    private ICocheDAO cocheDAO;
    @Autowired
    private IMotorCombustionDAO motorCombustionDAO;
    @Autowired
    private IMotorElectricoDAO motorElectricoDAO;

    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public List<Coche> findAllCoches() {
        return cocheDAO.findAll();
    }

    @Override
    public List<MotorConbustion> findAllMotorCombustion() {
        return motorCombustionDAO.findAll();
    }

    @Override
    public List<MotorElectrico> findAllMotorElectrico() {
        return motorElectricoDAO.findAll();
    }
}
