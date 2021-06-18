package br.com.lucio.logapi.api.exceptionhandler;

public enum MensagemErro {
	
	CAMPOS_INVALIDOS("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."),
	MENSAGEM_INVALIDA("Mensagem inválida");
	
	private final String mensagem;
	MensagemErro(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getMensagem() {		 
		return this.mensagem;
	}
}

