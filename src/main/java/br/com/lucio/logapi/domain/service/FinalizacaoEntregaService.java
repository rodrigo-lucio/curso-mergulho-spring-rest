package br.com.lucio.logapi.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.lucio.logapi.domain.exception.NegocioException;
import br.com.lucio.logapi.domain.model.Entrega;
import br.com.lucio.logapi.domain.model.StatusEntrega;
import br.com.lucio.logapi.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FinalizacaoEntregaService {

	private BuscaEntregaService buscaEntregaService;
	private EntregaRepository entregaRepository;

	@Transactional
	public void finalizar(Long entregaId) {
		Entrega entrega = buscaEntregaService.buscar(entregaId);
		if (naoPodeSerFinalizada(entrega)) {
			throw new NegocioException("Entrega não pode ser finalizada");
		}
		entrega.setStatus(StatusEntrega.FINALIZADA);
		entrega.setDataFinalizacao(OffsetDateTime.now());
		entregaRepository.save(entrega);
	}

	public boolean naoPodeSerFinalizada(Entrega entrega) {
		return !podeSerFinalizada(entrega);
	}

	public boolean podeSerFinalizada(Entrega entrega) {
		return entrega.getStatus().equals(StatusEntrega.PENDENTE);
	}

}
