package br.com.lucio.logapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucio.logapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.lucio.logapi.domain.model.Entrega;
import br.com.lucio.logapi.domain.repository.EntregaRepository;

@Service
public class BuscaEntregaService {

	@Autowired
	public EntregaRepository entregaRepository;

	public Entrega buscar(Long entregaId) {
		return entregaRepository.findById(entregaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException());
	}
	
}