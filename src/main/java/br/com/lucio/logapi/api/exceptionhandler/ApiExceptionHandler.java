package br.com.lucio.logapi.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.lucio.logapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.lucio.logapi.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Problema problema = populaDadosProblemaComListaCampos(MensagemErro.CAMPOS_INVALIDOS.getMensagem(), ex, status);
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Problema problema = populaDadosProblema(MensagemErro.MENSAGEM_INVALIDA.getMensagem(), ex.getMessage(), status);
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Problema problema = populaDadosProblema(MensagemErro.MENSAGEM_INVALIDA.getMensagem(), ex.getMessage(), status);
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Problema problema = populaDadosProblema(MensagemErro.MENSAGEM_INVALIDA.getMensagem(), ex.getMessage(), status);
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problema problema = populaDadosProblema(ex.getMessage(), status);
		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontradaException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problema problema = populaDadosProblema(ex.getMessage(), status);
		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	private Problema populaDadosProblemaComListaCampos(String titulo, MethodArgumentNotValidException ex,
			HttpStatus status) {
		List<Problema.Campo> campos = populaListaCamposComProblema(ex);
		Problema problema = populaDadosProblema(titulo, status);
		problema.setCampos(campos);
		return problema;
	}

	private Problema populaDadosProblema(String titulo, HttpStatus status) {
		return populaDadosProblema(titulo, null, status);
	}

	private Problema populaDadosProblema(String titulo, String mensagem, HttpStatus status) {
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(titulo);
		problema.setMensagem(mensagem);
		return problema;
	}

	private List<Problema.Campo> populaListaCamposComProblema(MethodArgumentNotValidException ex) {
		List<Problema.Campo> campos = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = ((FieldError) error).getDefaultMessage();
			campos.add(new Problema.Campo(nome, mensagem));
		}
		return campos;
	}

}
