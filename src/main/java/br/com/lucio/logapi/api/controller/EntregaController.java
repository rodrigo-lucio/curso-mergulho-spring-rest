package br.com.lucio.logapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucio.logapi.api.mapper.EntregaMapper;
import br.com.lucio.logapi.api.model.EntregaModel;
import br.com.lucio.logapi.api.model.input.EntregaInputModel;
import br.com.lucio.logapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.lucio.logapi.domain.model.Entrega;
import br.com.lucio.logapi.domain.repository.EntregaRepository;
import br.com.lucio.logapi.domain.service.FinalizacaoEntregaService;
import br.com.lucio.logapi.domain.service.SolicitacaoEntregaService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

	private SolicitacaoEntregaService solicitacaoEntregaService;
	private FinalizacaoEntregaService finalizacaoEntregaService;
	private EntregaRepository entregaRepository;
	private EntregaMapper entregaMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaModel criar(@Valid @RequestBody EntregaInputModel entrega) {
		Entrega novaEntrega = entregaMapper.toEntity(entrega);		
		Entrega entregaSolicitada = solicitacaoEntregaService.solicitar(novaEntrega);
		return entregaMapper.toModel(entregaSolicitada);
	}
	
	@GetMapping
	public List<EntregaModel> listar() {
		List<Entrega> entregas = entregaRepository.findAll();
		return entregaMapper.toCollectionModel(entregas);		 
	}

	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaModel> buscar(@PathVariable Long entregaId){
		return entregaRepository.findById(entregaId)
				.map(entrega -> ResponseEntity.ok(entregaMapper.toModel(entrega)))					
				.orElseThrow(() -> new EntidadeNaoEncontradaException());
	}
	
	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long entregaId) {
		finalizacaoEntregaService.finalizar(entregaId);
	}
	
}
