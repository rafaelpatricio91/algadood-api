package com.rafa.algafood.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 4458300969991036210L;

	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
}
