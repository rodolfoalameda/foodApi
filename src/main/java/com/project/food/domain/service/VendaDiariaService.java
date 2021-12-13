package com.project.food.domain.service;

import com.project.food.domain.model.dtos.VendaDiariaDTO;
import com.project.food.repository.filters.VendaDiariaFilters;

import java.util.List;

public interface VendaDiariaService {

    List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilters filters, String timeOffSet);
}
