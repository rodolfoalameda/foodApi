package com.project.food.domain.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormaDePagamentoInput {

    @NotBlank
    private String descricao;

}
