package com.project.food.controller.assembler;

import com.project.food.domain.model.Cidade;
import com.project.food.domain.model.dtos.CidadeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTO fromDomainToDto(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> fromDomainsToDtos(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> fromDomainToDto(cidade)).collect(Collectors.toList());
    }

}
