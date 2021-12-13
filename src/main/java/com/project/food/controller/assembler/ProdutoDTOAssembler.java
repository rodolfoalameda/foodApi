package com.project.food.controller.assembler;

import com.project.food.domain.model.Estado;
import com.project.food.domain.model.Produto;
import com.project.food.domain.model.dtos.EstadoDTO;
import com.project.food.domain.model.dtos.ProdutoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO fromDomainToDto(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> fromDomainsToDtos(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> fromDomainToDto(produto))
                .collect(Collectors.toList());
    }

}
