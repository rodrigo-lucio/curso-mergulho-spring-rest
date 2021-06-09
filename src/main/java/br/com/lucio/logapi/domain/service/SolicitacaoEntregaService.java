package br.com.lucio.logapi.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lucio.logapi.domain.model.Cliente;
import br.com.lucio.logapi.domain.model.Entrega;
import br.com.lucio.logapi.domain.model.StatusEntrega;
import br.com.lucio.logapi.domain.repository.EntregaRepository;

@Service
public class SolicitacaoEntregaService {
	
	@Autowired
	private EntregaRepository entregaRepository;

	@Autowired
	private CatalagoClienteService catalagoClienteService;
	
	@Transactional
	public Entrega solicitar(Entrega entrega) {				
		Cliente cliente = catalagoClienteService.buscarPorId(entrega.getCliente().getId());
		entrega.setCliente(cliente);
		entrega.setStatus(StatusEntrega.PENDENTE);
		entrega.setDataPedido(OffsetDateTime.now());		
		return entregaRepository.save(entrega);
	}

}
