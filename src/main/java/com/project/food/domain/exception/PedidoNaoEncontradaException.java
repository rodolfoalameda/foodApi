package com.project.food.domain.exception;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public PedidoNaoEncontradaException(Long id){
		this(String.format("Não existe um cadastro de pedido com codigo %d", id));
	}
}
