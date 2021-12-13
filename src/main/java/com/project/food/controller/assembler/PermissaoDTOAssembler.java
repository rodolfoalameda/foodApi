package com.project.food.controller.assembler;

import com.project.food.domain.model.Permissao;
import com.project.food.domain.model.dtos.PermissaoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDTO fromDomainToDto(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> fromDomainsToDtos(Collection<Permissao> permissaos) {
        return permissaos.stream()
                .map(permissao -> fromDomainToDto(permissao))
                .collect(Collectors.toList());
    }

}
