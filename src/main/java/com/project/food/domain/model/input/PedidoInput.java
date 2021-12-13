package com.project.food.domain.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoInput {

    @Valid
    @NotNull
    private RestauranteIdInput restaurante;

    @Valid
    @NotNull
    private EnderecoInput endereco;

    @NotNull
    @Valid
    private FormaPagamentoIdInput formaPagamento;

    @Size(min = 1)
    private List<ItemPedidoInput> pedido;


}
