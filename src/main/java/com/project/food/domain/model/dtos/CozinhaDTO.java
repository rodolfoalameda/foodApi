package com.project.food.domain.model.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.food.domain.model.views.RestauranteView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaDTO {

    @JsonView(RestauranteView.Resumo.class)
    private Long id;
    @JsonView(RestauranteView.Resumo.class)
    private String nome;
}
