package br.com.lucio.logapi.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.lucio.logapi.api.model.EntregaModel;
import br.com.lucio.logapi.api.model.input.EntregaInputModel;
import br.com.lucio.logapi.domain.model.Entrega;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EntregaMapper {

	private ModelMapper modelMapper;

	public EntregaModel toModel(Entrega entrega) {
//		EntregaModel entregaModel = new EntregaModel();
//		
//		entregaModel.setId(entrega.getId());
//		entregaModel.setNomeCliente(entrega.getCliente().getNome());
//		entregaModel.setDestinatario(new DestinatarioModel());
//		entregaModel.getDestinatario().setNome(entrega.getDestinatario().getNome());
//		entregaModel.getDestinatario().setLogradouro(entrega.getDestinatario().getLogradouro());
//		entregaModel.getDestinatario().setBairro(entrega.getDestinatario().getBairro());
//		entregaModel.getDestinatario().setNumero(entrega.getDestinatario().getNumero());
//		entregaModel.getDestinatario().setComplemento(entrega.getDestinatario().getComplemento());
//		
//		entregaModel.setTaxa(entrega.getTaxa());
//		entregaModel.setStatus(entrega.getStatus());
//		entregaModel.setDataFinalizacao(entrega.getDataFinalizacao());
//		entregaModel.setDataPedido(entrega.getDataPedido());
//		
//		return entregaModel;

		// Substitui as linhas de cima fazendo o mapper
		return modelMapper.map(entrega, EntregaModel.class);
	}

	public List<EntregaModel> toCollectionModel(List<Entrega> entregas) {
		return entregas.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Entrega toEntity(EntregaInputModel entregaInputModel) {
		return modelMapper.map(entregaInputModel, Entrega.class);
	}
}
