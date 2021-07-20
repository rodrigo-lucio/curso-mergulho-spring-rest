package br.com.lucio.logapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucio.logapi.api.mapper.OcorrenciaMapper;
import br.com.lucio.logapi.api.model.OcorrenciaModel;
import br.com.lucio.logapi.api.model.input.OcorrenciaInput;
import br.com.lucio.logapi.domain.model.Entrega;
import br.com.lucio.logapi.domain.model.Ocorrencia;
import br.com.lucio.logapi.domain.service.BuscaEntregaService;
import br.com.lucio.logapi.domain.service.RegistroOcorrenciaService;

@RestController
@RequestMapping("entregas/{entregaId}/ocorrencias")
public class OcorrenciaController {

	@Autowired
	private RegistroOcorrenciaService registroOcorrencia;

	@Autowired
	private BuscaEntregaService buscaEntregaService;
	
	@Autowired
	private OcorrenciaMapper ocorrenciaMapper;
	
	@GetMapping
	public List<OcorrenciaModel> listar(@PathVariable Long entregaId){
		Entrega entrega = buscaEntregaService.buscar(entregaId);
		return ocorrenciaMapper.toCollectionModel(entrega.getOcorrencias());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OcorrenciaModel registrar(@PathVariable Long entregaId, @Valid @RequestBody OcorrenciaInput ocorrenciaInput) {
		Ocorrencia ocorrenciaRegistrada = registroOcorrencia.registrar(entregaId, ocorrenciaInput.getDescricao());
		return ocorrenciaMapper.toModel(ocorrenciaRegistrada);
	}
}
