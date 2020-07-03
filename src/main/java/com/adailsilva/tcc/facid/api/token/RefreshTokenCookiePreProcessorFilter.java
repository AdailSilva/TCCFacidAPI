package com.adailsilva.tcc.facid.api.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// Movendo o refresh token do cookie para a requisição.

@Component // Componente do Spring.
// Ordered.HIGHEST_PRECEDENCE alta prioridade.
// Para analizar a requisição antes de tudo e saber se tem o "refresh_token"
// com "grant_type" "/oauth/token" e tenha o cookie.
// Será preciso adicionar o token que está no cookie na requisição pra poder funcionar a aplicação.
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter { // Filtro comum da api de servlets.

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		// Verificação se uma requisição tem o end-point "/oauth/token"
		// Pois já é possível que por isso tenho o "refresh_token" na mesma.
		// ou Seja se tem o parâmentro "grant_type" com o valor "refresh_token".
		// pois só é usado o "refresh_token" do cookie quando
		// o parâmetro for "grant_type" com o valor "refresh_token".
		// Por fim Se existe algum cookie na requisição.
		// Se o nome do Cookie for "refreshToken" pego o seu valor.
		// o valor é a chave. (refresh_token).
		if ("/oauth/token".equalsIgnoreCase(req.getRequestURI())
				&& "refresh_token".equals(req.getParameter("grant_type")) && req.getCookies() != null) {
			for (Cookie cookie : req.getCookies()) {

				// Cookies capturados:
				logDetalhesCookie(cookie);

				if (cookie.getName().equals("refreshToken")) {
					String refreshToken = cookie.getValue();
					// Colocar agora o "refreshToken" dentro do mapa de parâmentros da requisição.
					// Porém na requisição original não é possível alterar os parâmetros.
					// SOLUÇÃO criar uma classe "MyServletRequestWrapper.class".
					req = new MyServletRequestWrapper(req, refreshToken); // Sobrescrevendo a requisição.
				}
			}
		}

		// Continuar a cadeia do Filtro:
		chain.doFilter(req, response);
	}

	@Override
	public void destroy() {

	}

	public void logDetalhesCookie(Cookie cookie) {

		System.out.println();
		System.out.println("========================================================");
		System.out.println("=============== Log Cookie Capturado: ==================");
		System.out.println("========================================================");

		String comment = cookie.getComment();
		System.out.println("Comment: " + comment);

		String domain = cookie.getDomain();
		System.out.println("Domain: " + domain);

		int maxAge = cookie.getMaxAge();
		System.out.println("MaxAge: " + maxAge);

		String name = cookie.getName();
		System.out.println("Name: " + name);

		String path = cookie.getPath();
		System.out.println("Path: " + path);

		String value = cookie.getValue();
		System.out.println("Value: " + value);

		int version = cookie.getVersion();
		System.out.println("Version: " + version);

		Object _class = cookie.getClass();
		System.out.println("Class: " + _class);

		boolean secure = cookie.getSecure();
		System.out.println("Secure: " + secure);

	}

	// CLASSE para Alteração da Requisição orignal. Também recebo o "refreshToken".
	static class MyServletRequestWrapper extends HttpServletRequestWrapper {

		// Recebendo o "refreshToken" abaixo para armazená-lo aqui após alteração.
		private String refreshToken;

		// public MyServletRequestWrapper(ServletRequest request) {
		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}

		// A requisição é agora um "new MyServletRequestWrapper".
		// Para quando a app precisar do "ParameterMap" ser passado outro.
		@Override
		public Map<String, String[]> getParameterMap() {
			// Já iniciado com os valores da requisição original
			// "getRequest().getParameterMap()".
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put("refresh_token", new String[] { refreshToken });
			map.setLocked(true); // Travando o map.
			// return super.getParameterMap();
			return map;
		}
	}

}
