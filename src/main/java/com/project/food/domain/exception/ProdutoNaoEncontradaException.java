package com.project.food.domain.exception;

public class ProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public ProdutoNaoEncontradaException(Long id, Long restauranteId){
		this(String.format("Não existe um cadastro de produto com codigo %d no restaurante %d" , id, restauranteId));
	}
}
