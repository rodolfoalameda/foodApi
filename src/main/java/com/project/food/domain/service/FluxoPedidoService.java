package com.project.food.domain.service;

public interface FluxoPedidoService {

    void confirmar(Long peididoId);

    void entregar(Long pedidoId);

    void cancelar(Long pedidoId);

}
