package br.com.lucio.logapi.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DestinatarioInput {

	@NotBlank	
	private String nome;
	
	@NotBlank	
	private String logradouro;
	
	@NotBlank	
	private String numero;
		
	@NotBlank	
	private String bairro;
	
	private String complemento;
	
}
