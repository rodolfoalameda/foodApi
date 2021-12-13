package com.project.food.controller.assembler;

import com.project.food.domain.model.Pedido;
import com.project.food.domain.model.dtos.PedidoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO fromDomainToDto(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> fromDomainsToDtos(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> fromDomainToDto(pedido))
                .collect(Collectors.toList());
    }

}
