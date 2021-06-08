package br.com.lucio.logapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lucio.logapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.lucio.logapi.domain.exception.NegocioException;
import br.com.lucio.logapi.domain.model.Cliente;
import br.com.lucio.logapi.domain.repository.ClienteRepository;
import br.com.lucio.logapi.domain.repository.EntregaRepository;

@Service
public class CatalagoClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EntregaRepository entregaRepository;
	
	
	public Cliente buscarPorId(Long clienteId){
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException());
	}
	
	@Transactional
	public Cliente salvar(Cliente cliente) {		
		boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
		if (emailEmUso) {
			throw new NegocioException("Já existe um cliente cadastrado com o e-mail informado.");
		}
		return clienteRepository.save(cliente);
	}

	@Transactional
	public void excluir(Long clienteId) {
		if(!clienteRepository.existsById(clienteId)) {
			throw new EntidadeNaoEncontradaException();
		}		
		if(entregaRepository.findByClienteId(clienteId).size() > 0) {
			throw new NegocioException("Nao pode remover cliente que possui entrega");		
		}
		clienteRepository.deleteById(clienteId);
	}
	
	
}
