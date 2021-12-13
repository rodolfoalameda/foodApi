package com.project.food.controller;

import com.project.food.domain.model.dtos.VendaDiariaDTO;
import com.project.food.domain.service.VendaDiariaService;
import com.project.food.repository.filters.VendaDiariaFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    @Autowired
    private VendaDiariaService vendaDiariaService;

    @GetMapping("vendas-diarias")
    public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilters filters,
                                                       @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        return vendaDiariaService.consultarVendasDiarias(filters, timeOffSet);
    }

}
