package com.project.food.domain.service;

import com.project.food.domain.model.Produto;

public interface ProdutoService {

    Produto save(Produto produto);

    Produto findOrFail(Long restauranteId, Long produtoId);


}
