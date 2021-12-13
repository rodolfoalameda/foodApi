package com.project.food.controller.assembler;

import com.project.food.domain.model.Grupo;
import com.project.food.domain.model.dtos.GrupoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTO fromDomainToDto(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    public List<GrupoDTO> fromDomainsToDtos(Collection<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> fromDomainToDto(grupo))
                .collect(Collectors.toList());
    }

}
