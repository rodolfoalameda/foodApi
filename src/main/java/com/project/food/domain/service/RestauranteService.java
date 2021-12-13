package com.project.food.domain.service;

import com.project.food.domain.model.Restaurante;

public interface RestauranteService {

    void delete(Long id);

    Restaurante save(Restaurante restaurante);

    Restaurante findOrFail(Long id);

    void ativar(Long id);

    void inativar(Long id);

    void removerFormaPagamento(Long restauranteId, Long formaPagamentoId);

    void associarFormaPagamento(Long restauranteId, Long formaPagamentoId);

    void abrir(Long id);

    void fechar(Long id);

    void associarResponsavel(Long restauranteId, Long usuarioId);

    void desassociarResponsavel(Long restauranteId, Long usuarioId);

}
 