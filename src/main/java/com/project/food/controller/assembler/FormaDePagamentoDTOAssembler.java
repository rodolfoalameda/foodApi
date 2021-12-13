package com.project.food.controller.assembler;

import com.project.food.domain.model.FormaPagamento;
import com.project.food.domain.model.dtos.FormaPagamentoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaDePagamentoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO fromDomainToDto(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> fromDomainsToDtos(Collection<FormaPagamento> formaPagamentos) {
        return formaPagamentos.stream()
                .map(formaPagamento -> fromDomainToDto(formaPagamento))
                .collect(Collectors.toList());
    }

}
