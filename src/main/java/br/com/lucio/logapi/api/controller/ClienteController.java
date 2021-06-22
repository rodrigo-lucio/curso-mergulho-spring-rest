package br.com.lucio.logapi.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucio.logapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.lucio.logapi.domain.model.Cliente;
import br.com.lucio.logapi.domain.repository.ClienteRepository;
import br.com.lucio.logapi.domain.service.CatalagoClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CatalagoClienteService catalagoClienteService;	
	
	@GetMapping
	public Page<Cliente> listar(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}
	
	@GetMapping(params = "noPage")
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(clienteRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long id) {		
		return clienteRepository.findById(id)
				.map(ResponseEntity::ok) //	Poderia ser= .map(cliente -> ResponseEntity.ok(cliente))
				.orElseThrow(() -> new EntidadeNaoEncontradaException());			
// O Código acima substitui esse que esta comentado.		
//		Optional<Cliente> cliente = clienteRepository.findById(id);
//		if(cliente.isPresent()) {		
//			return ResponseEntity.ok(cliente.get());
//		}
//		
//		return new EntidadeNaoEncontradaException();;

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return catalagoClienteService.salvar(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @Valid @RequestBody Cliente cliente) {
		if(!clienteRepository.existsById(clienteId)) {
			throw new EntidadeNaoEncontradaException();
		}		
		cliente.setId(clienteId);
		cliente = catalagoClienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
		catalagoClienteService.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}
}