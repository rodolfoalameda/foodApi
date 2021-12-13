package com.project.food.domain.service;

import com.project.food.domain.model.FormaPagamento;

public interface FormaDePagamentoService {

    public void delete(Long id);

    public FormaPagamento save(FormaPagamento formaPagamento);

    public FormaPagamento findOrFail(Long id);



}
