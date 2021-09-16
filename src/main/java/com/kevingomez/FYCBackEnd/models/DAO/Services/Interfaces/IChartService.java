package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import java.util.HashMap;

public interface IChartService {
   HashMap<String, Object> getChartVolumen();
   void setChartVolumen(HashMap<String, Object> volumenes);
}
