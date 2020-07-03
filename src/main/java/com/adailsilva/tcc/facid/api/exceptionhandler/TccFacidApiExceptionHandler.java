package com.adailsilva.tcc.facid.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.adailsilva.tcc.facid.api.service.exception.PessoaInexistenteOuInativaException;

import lombok.Getter;

@ControllerAdvice // controlador que observa toda a aplicação.
public class TccFacidApiExceptionHandler extends ResponseEntityExceptionHandler {

	// "ResponseEntityExceptionHandler" captura Exceções de respostas de entidades.
	@Autowired
	private MessageSource messageSource;

	@Override // capturando as mensagens ilegíveis com o: "handleHttpMessageNotReadable".
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		// MENSAGEM PARA O USUÁRIO:
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());

		// MENSAGEM PARA O DESENVOLVEDOR:
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

		// Deixando este retorno como uma lista:
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	// Método para tratar argumentos de outros métodos que não sejam válidos:
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		// "ex.getBindingResult()" tem a lista de todos os erros:
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	// Passando um Array com todas as classes de exceções para tratamentos.
	// Podendo ser Várias, separadas com vírgula.

	// Método criado para tratar a Exceção: "NullPointerException".
	@ExceptionHandler({ NullPointerException.class })
	// Recebendo um Tipo específico de Exceção e um WebRequest request para tratar.
	public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {

		// MENSAGEM PARA O USUÁRIO:
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado.objeto-nulo", null,
				LocaleContextHolder.getLocale());

		// MENSAGEM PARA O DESENVOLVEDOR:
		String mensagemDesenvolvedor = ex.toString();

		// Deixando este retorno também como uma lista:
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request); // 404.
	}

	// Não existência de um recurso, após tentar remoção do mesmo, mais de uma vez:
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	// Recebendo um Tipo específico de Exceção e um WebRequest request para tratar.
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {

		// MENSAGEM PARA O USUÁRIO:
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null,
				LocaleContextHolder.getLocale());

		// MENSAGEM PARA O DESENVOLVEDOR:
		String mensagemDesenvolvedor = ex.toString();

		// Deixando este retorno também como uma lista:
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request); // 404.
	}

	// Campos inválidos no JSON em Relacionamentos:
	// Recebendo um Tipo específico de Exceção e um WebRequest request para tratar.
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {

		// MENSAGEM PARA O USUÁRIO:
		String mensagemUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null,
				LocaleContextHolder.getLocale());

		// MENSAGEM PARA O DESENVOLVEDOR:
		// <!-- ExceptionUtils.getRootCauseMessage(ex) -->
		// <!-- Dando uma mensagem da causa raíz da exceção -->
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		// Deixando este retorno também como uma lista:
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request); // 400.
	}

	// Pessoa Nula ou Inativa:
	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	// Não precisa receber um "WebRequest request".
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {

		// MENSAGEM PARA O USUÁRIO:
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null,
				LocaleContextHolder.getLocale());

		// MENSAGEM PARA O DESENVOLVEDOR:
		String mensagemDesenvolvedor = ex.toString();

		// Deixando este retorno também como uma lista:
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	// Lista de possíveis erros para retornos se existir mais de um erro:
	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();
		
		// "bindingResult" tem a lista de todos os erros
		// (todos os erros nos campos, fields validados):		
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}

		return erros;
	}

	// CLASSE específica para os possíveis erros:
	public static class Erro {

		@Getter
		private String mensagemUsuario;

		@Getter
		private String mensagemDesenvolvedor;

		// Construtor:
		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			// super();
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

	}
}
