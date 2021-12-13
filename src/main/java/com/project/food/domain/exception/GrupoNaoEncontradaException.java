package com.project.food.domain.exception;

public class GrupoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public GrupoNaoEncontradaException(Long id){
		this(String.format("Não existe um cadastro de grupo com codigo %d", id));
	}
}
