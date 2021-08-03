package br.com.lucio.logapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucio.logapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.lucio.logapi.domain.exception.NegocioException;
import br.com.lucio.logapi.domain.model.Cliente;
import br.com.lucio.logapi.domain.service.CatalagoClienteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroClienteApiITest {

	@Autowired
	private CatalagoClienteService catalagoClienteService;

	@Test
	public void deveAtribuirIdECadastrarUmClienteComSucesso_QuandoClienteCorreto() {
		Cliente cliente =  Cliente.builder()
				.nome("Cliente de teste")
				.email(String.valueOf(LocalDateTime.now()).replace(":", "") + "@lucio.com")
				.telefone("559966996699").build();
		Cliente novoCliente = catalagoClienteService.salvar(cliente);

		assertThat(novoCliente).isNotNull();
		assertThat(novoCliente.getId()).isNotNull();
	}

	@Test(expected = ConstraintViolationException.class)
	public void deveFalharAoCadastrarCliente_QuandoSemNome() {
		Cliente cliente =  Cliente.builder()
				.nome(null)
				.email("testetesteteste")
				.telefone("559966996699").build();
		catalagoClienteService.salvar(cliente);
	}

	@Test(expected = ConstraintViolationException.class)
	public void deveFalharAoCadastrarCliente_QuandoEmailInvalido() {
		Cliente cliente =  Cliente.builder()
				.nome("Joao da silva")
				.email("testetesteteste")
				.telefone("559966996699").build();
		catalagoClienteService.salvar(cliente);
	}

	@Test
	public void deveFalharAoCadastrarCliente_QuandoEmailDuplicado() {
		Cliente cliente =  Cliente.builder()
				.nome("Joao da silva")
				.email("Luciodigo@gmail.com")
				.telefone("559966996699").build();
	
		try {
			catalagoClienteService.salvar(cliente);
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Já existe um cliente cadastrado com o e-mail informado."));
		}
			 
	}

	@Test(expected = EntidadeNaoEncontradaException.class)
	public void deveFalharAoRemoverCliente_QuandoIdInexistente() {
		catalagoClienteService.excluir(9999999L);
	}

}
