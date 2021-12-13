package com.project.food.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.food.domain.model.Cozinha;
import com.project.food.domain.model.Endereco;
import com.project.food.domain.model.FormaPagamento;
import com.project.food.domain.model.Produto;

import java.time.OffsetDateTime;
import java.util.List;

public abstract class RestauranteMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private List<FormaPagamento> formasPagamentos;

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private OffsetDateTime dataCadastro;

    @JsonIgnore
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    private List<Produto> produtos;

}
