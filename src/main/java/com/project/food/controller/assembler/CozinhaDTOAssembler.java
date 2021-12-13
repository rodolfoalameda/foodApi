package com.project.food.controller.assembler;

import com.project.food.domain.model.Cozinha;
import com.project.food.domain.model.dtos.CozinhaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO fromDomainToDto(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    public List<CozinhaDTO> fromDomainsToDtos(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> fromDomainToDto(cozinha)).collect(Collectors.toList());
    }

}
