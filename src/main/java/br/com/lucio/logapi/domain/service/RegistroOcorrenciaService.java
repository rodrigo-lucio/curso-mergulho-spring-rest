package br.com.lucio.logapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucio.logapi.domain.model.Entrega;
import br.com.lucio.logapi.domain.model.Ocorrencia;

@Service
public class RegistroOcorrenciaService {

	
	@Autowired
	private BuscaEntregaService buscaEntregaService;

	@Transactional // Nao precisa fazer o save, o proprio JPA sincroniza as alterações com o transactional
	public Ocorrencia registrar(Long entregaId, String descricao) {
		Entrega entrega = buscaEntregaService.buscar(entregaId);		
		return entrega.adicionarOcorrencia(descricao);		
	}
		
}
