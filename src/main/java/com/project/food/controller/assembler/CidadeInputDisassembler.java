package com.project.food.controller.assembler;

import com.project.food.domain.model.Cidade;
import com.project.food.domain.model.Estado;
import com.project.food.domain.model.input.CidadeInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade fromModelToDomain(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInput, cidade);
    }

}
