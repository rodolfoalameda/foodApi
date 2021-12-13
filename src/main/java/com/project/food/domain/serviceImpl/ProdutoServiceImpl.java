package com.project.food.domain.serviceImpl;

import com.project.food.domain.exception.ProdutoNaoEncontradaException;
import com.project.food.domain.model.Produto;
import com.project.food.domain.service.ProdutoService;
import com.project.food.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    @Transactional
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public Produto findOrFail(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradaException(restauranteId, produtoId));
    }
}
