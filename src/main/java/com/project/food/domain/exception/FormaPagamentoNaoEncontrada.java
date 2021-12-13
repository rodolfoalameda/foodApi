package com.project.food.domain.exception;

public class FormaPagamentoNaoEncontrada extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEncontrada(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontrada(Long id) {
        this(String.format("Não existe tal forma de pagamento %d", id));
    }
}
