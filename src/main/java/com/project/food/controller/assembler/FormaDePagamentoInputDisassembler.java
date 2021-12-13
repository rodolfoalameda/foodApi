package com.project.food.controller.assembler;

import com.project.food.domain.model.FormaPagamento;
import com.project.food.domain.model.input.FormaDePagamentoInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaDePagamentoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento fromModelToDomain(FormaDePagamentoInput formaDePagamentoInput) {
        return modelMapper.map(formaDePagamentoInput, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaDePagamentoInput formaDePagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaDePagamentoInput, formaPagamento);
    }
}
