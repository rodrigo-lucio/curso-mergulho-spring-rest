package br.com.lucio.logapi.domain.exception;

public class EntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);	
	}

	public EntidadeNaoEncontradaException() {
		super("Recurso não encontrado");	
	}
	
}
