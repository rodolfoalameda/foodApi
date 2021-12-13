package com.project.food.domain.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoInput {

    @NotNull
    private String cep;

    @NotNull
    private String logradouro;

    @NotNull
    private String numero;

    private String complemento;

    @NotNull
    private String bairro;

    @NotNull
    @Valid
    private CidadeIdInput cidade;
}
