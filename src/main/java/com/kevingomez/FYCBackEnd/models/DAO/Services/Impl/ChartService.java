package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IChartService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ChartService implements IChartService {

    private HashMap<String, Object> volumenes = new HashMap<>();

    @Override
    public void setChartVolumen(HashMap<String, Object> volumenes){
        this.volumenes = volumenes;
    }
    @Override
    public HashMap<String, Object> getChartVolumen(){
        return volumenes;
    }
}
