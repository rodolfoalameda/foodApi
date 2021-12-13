package com.project.food.controller.assembler;

import com.project.food.domain.model.Estado;
import com.project.food.domain.model.dtos.EstadoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoDTO fromDomainToDto(Estado estado) {
        return modelMapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> fromDomainsToDtos(List<Estado> estados) {
        return estados.stream()
                .map(estado -> fromDomainToDto(estado))
                .collect(Collectors.toList());
    }

}
