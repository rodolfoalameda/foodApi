package com.project.food.controller.assembler;

import com.project.food.domain.model.Estado;
import com.project.food.domain.model.Produto;
import com.project.food.domain.model.input.EstadoInput;
import com.project.food.domain.model.input.ProdutoInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Produto fromModelToDomain(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }

}
