package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Modelo;
import com.kevingomez.FYCBackEnd.models.filters.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.regex.Matcher;

public interface IFiltrosService {
    Filter estructurarFiltros(Matcher mat);
    Page<Modelo> filtrarModelos(Pageable pageable, Filter filtros);
}
