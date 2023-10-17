package com.rafa.algafood.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {

	private static final long serialVersionUID = -1445061840426971548L;
	
	public EntidadeEmUsoException(String mensagem) {
		super(mensagem);
	}

}
