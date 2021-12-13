package com.project.food.domain.service;

import com.project.food.domain.model.Pedido;

public interface PedidoService {

    Pedido findOrFail(Long id);

    Pedido emitir(Pedido pedido);

    void validarPedido(Pedido pedido);

    void validarItens(Pedido pedido);

}
