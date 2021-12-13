package com.project.food.domain.model.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.food.domain.model.views.RestauranteView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteDTO {

    @JsonView(RestauranteView.Resumo.class)
    private Long id;
    @JsonView(RestauranteView.Resumo.class)
    private String nome;
    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;
    @JsonView(RestauranteView.Resumo.class)
    private CozinhaDTO cozinha;
    private Boolean ativo;
    private EnderecoDTO endereco;
    private Boolean aberto;

}
