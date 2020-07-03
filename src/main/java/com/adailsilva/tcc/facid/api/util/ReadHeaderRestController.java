package com.adailsilva.tcc.facid.api.util;

// import java.net.InetSocketAddress;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
// import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Link Base: https://www.baeldung.com/spring-rest-http-headers
@RestController // Já converte para JSON como Representação padrão do Recurso.
// "@CrossOrigin" Poderia ser para toda a Classe
// @CrossOrigin(maxAge = 10, origins = { "http://localhost:8000" })
public class ReadHeaderRestController {
	private static final Log LOG = LogFactory.getLog(ReadHeaderRestController.class);

	@GetMapping("/cumprimento")
	public ResponseEntity<String> cumprimento(@RequestHeader(value = "accept-language") String linguagem) {
		
		String cumprimento = "";
		String primeiroIdioma = (linguagem.length() > 1 ? linguagem.substring(0, 2) : linguagem);
		switch (primeiroIdioma) {
		case "es":
			cumprimento = "Hola!";
			break;
		case "de":
			cumprimento = "Hallo!";
			break;
		case "fr":
			cumprimento = "Bonjour!";
			break;
		case "en":
		default:
			cumprimento = "Hello!";
			break;
		}

		return new ResponseEntity<String>(cumprimento, HttpStatus.OK);
	}

	@GetMapping("/double")
	public ResponseEntity<String> numeroDouble(@RequestHeader("my-number") int meuNumero) {
		
		return new ResponseEntity<String>(String.format("%d * 2 = %d", meuNumero, (meuNumero * 2)), HttpStatus.OK);
	}

	@GetMapping("/listarCabecalhos")
	public ResponseEntity<String> listarTodosCabecalhos(@RequestHeader Map<String, String> cabecalhos) {

		cabecalhos.forEach((key, value) -> {
			LOG.info(String.format("Header '%s' = %s", key, value));
		});

		return new ResponseEntity<String>(String.format("Listado %d cabeçalhos.", cabecalhos.size()), HttpStatus.OK);
	}

	@GetMapping("/valorMultiplo")
	public ResponseEntity<String> valorMultiplo(@RequestHeader MultiValueMap<String, String> cabecalhos) {
		
		cabecalhos.forEach((key, value) -> {
			LOG.info(String.format("Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
		});

		return new ResponseEntity<String>(String.format("Listado %d cabeçalhos.", cabecalhos.size()), HttpStatus.OK);
	}

	/*
	 * @GetMapping("/pegarUrl") public ResponseEntity<String>
	 * pegarUrl(@RequestHeader HttpHeaders cabecalhos) { InetSocketAddress host =
	 * cabecalhos.getHost(); String url = "http://" + host.getHostName() + ":" +
	 * host.getPort(); return new
	 * ResponseEntity<String>(String.format("Base URL = %s", url), HttpStatus.OK); }
	 */

	@GetMapping("/cabecalhoNaoObrigatorio")
	public ResponseEntity<String> avaliarCabecalhoNaoObrigatorio(
			@RequestHeader(value = "optional-header", required = false) String cabecalhoOpcional) {

		return new ResponseEntity<String>(
				String.format("O cabeçalho opcional estava presente? %s!", (cabecalhoOpcional == null ? "Não" : "Sim")),
				HttpStatus.OK);
	}

	@GetMapping("/padrao")
	public ResponseEntity<String> avaliarValorPadraoCabecalho(
			@RequestHeader(value = "optional-header", defaultValue = "3600") int cabecalhoOpcional) {

		return new ResponseEntity<String>(String.format("Cabeçalho opcional é %d.", cabecalhoOpcional), HttpStatus.OK);
	}
}
