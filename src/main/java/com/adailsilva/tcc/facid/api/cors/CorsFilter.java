package com.adailsilva.tcc.facid.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// import com.adailsilva.tcc.facid.api.config.property.TccFacidApiProperty;

// Criando filtro para CORS.
@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Filtro de alta prioridade.
public class CorsFilter implements Filter {

	// TODO: Configurar para diferentes ambientes.
	private String originPermitida = "http://localhost:8000";
	// Após implementação da OriginPermitida em "TccFacidApiProperty.class"
	
//	@Autowired
//	private TccFacidApiProperty tccFacidApiProperty;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		// Conversão do Servlet request para HttpServletResquet e HttpServletResponse
		HttpServletRequest request = (HttpServletRequest) req; // Cast entre tipos.
		HttpServletResponse response = (HttpServletResponse) resp; // Cast entre tipos.

		// Outros Headers importantes:
		// ESTÃO FORA DO OPTIONS POIS SEMPRE TERÃO DE SER ENVIADOS.
		// Para que outros funcionem como POST, GET, PUT, DELETE...
//		response.setHeader("Access-Control-Allow-Origin", tccFacidApiProperty.getOriginPermitida());
		
		response.setHeader("Access-Control-Allow-Origin", originPermitida);		
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		// " "Access-Control-Allow-Credentials", "true"
		// por conta do cookie do refresh token. assim o cookie é enviado.

		// Se a Requisição for um "OPTIONS" permite a "pre failed request".
		// Se não continua com o filtro normal.
		// Se for o OPTIONS não pode deixar continuar pois o Spring Security irá
		// bloquear a requisição.
		// Também verificar se a origem permitida é a mesma origem da requisição.
		
		System.out.println();
		System.out.println("=== Requisição recebida, aplicando CORS Filter ===");
		System.out.println();
		
		if ("OPTIONS".equals(request.getMethod()) &&
		originPermitida.equals(request.getHeader("Origin"))) {
			
			System.out.println();
			System.out.println("=== FILTRO CORS ===");
			System.out.println("Método da Requisição: " + request.getMethod());
			System.out.println("Header da Requisição é Origin e o seu valor é: " + request.getHeader("Origin"));
			System.out.println();
			
		// Após implementação da OriginPermitida em "TccFacidApiProperty.class"
//		if ("OPTIONS".equals(request.getMethod())
//				&& tccFacidApiProperty.getOriginPermitida().equals(request.getHeader("Origin"))) {
			// response2.setHeader(name, value);
			// 1º Métodos HTTP Suportados.
			// 2º Headers HTTP Suportados.
			// 3º Tempo que o Browser irá fazer a próxima requisição.
			// em cache "3600" SEGUNDOS, ou seja UMA HORA.
			// RESUMO: O CORS adiciona estes Headers HTTP.
			// Header de resposta começam com "Access-Control", aqui a verificação se resume
			// em constatar se origem permitida é igual a origem que veio do browser e for
			// uma requisição "OPTIONS", acrescentado é este Headers e devolve "status OK"
			// para a requisição posterior poder ser concluída.
			// Se não for Bloqueia e a police do JavaScript não permitirá ir a frente.

			response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");			 
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			response.setHeader("Access-Control-Max-Age", "3600");
			
			// Se OPTIONS e Configurações aceitas, Resposta OK:
			response.setStatus(HttpServletResponse.SC_OK);
			System.out.println("=== Fim do Filtro Cross-origin resource sharing (CORS) ===");
			System.out.println();
		} else {
			
			// Continuar a cadeia do Filtro (Fluxo da requisição):
			chain.doFilter(req, resp);
		}

	}

	@Override
	public void destroy() {

	}

}
