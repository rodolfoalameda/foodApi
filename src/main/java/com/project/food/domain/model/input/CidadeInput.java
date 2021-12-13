package com.project.food.domain.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInput {

    @NotNull
    public String nome;

    @NotNull
    public EstadoIdInput estado;

}
