package com.adailsilva.tcc.facid.api.security.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.DispatcherType;
// import javax.servlet.Filter;
// import javax.servlet.FilterChain;
// import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
// import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
// import javax.servlet.ServletRequest;
// import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Filtro de alta prioridade.
// public class LoggerInterceptor implements HandlerInterceptor, Filter { // CORS
public class LoggerInterceptor implements HandlerInterceptor {

	// INTERCEPTOR:

	Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		System.out.println();
		System.out.println();
		log.info("Antes da execução do manipulador.");

		logDetalhesRequisicao(request);

		System.out.println();
		System.out.println("========================================================");
		System.out.println("===================== AUTENTICAÇÃO =====================");
		System.out.println("========================================================");

		// VERIFICAÇÕES:
		// Checando se a requisição é um Uplink:
		boolean isUplink = autenticarRequisicaoUplink(request, response, "162100594", "adail101@hotmail.com", "@Hacker101");

		// Info Request:
		preHandleSemObjectHandler(request, response);

		// Atributos com valores para comparação:
		String verbo = "POST";
		String uri = "/rawbodies/uplinks";

		if (verbo.equals(request.getMethod()) && uri.equals(request.getRequestURI()) && isUplink) {
			return true;
		} else if (verbo.equals(request.getMethod()) && uri.equals(request.getRequestURI()) && !isUplink) {
			System.out.println();
			System.err.println("Erro de Autenticação, a requisição não pode prosseguir!");
			return false;
		}

		System.out.println();
		System.out.println("========================================================");
		System.out.println("================= FIM DA AUTENTICAÇÃO! =================");
		System.out.println("========================================================");

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {

		System.out.println();
		log.info("A execução do manipulador está concluída.");

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3)
			throws Exception {

		System.out.println();
		log.info("Requisição concluída!");

	}

	// Verificação do tipo de Requisição, Procurando as de Uplinks:
	public boolean autenticarRequisicaoUplink(HttpServletRequest request, HttpServletResponse response,
			String customHeader, String login, String senha) throws Exception {

		// Autenticação Básica para Uplinks:
		boolean prosseguir = false;

		String verboHttp = request.getMethod();
		System.out.println("Verbo HTTP: " + verboHttp);

		String valorCustomHeader = request.getHeader("Facid");

		System.out.println("Valor do cabeçalho personalizado: " + valorCustomHeader);

		String authorization = request.getHeader("Authorization");
		System.out.println("Authorization: " + authorization);

		if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
			// Authorization = Basic base64credentials
			String base64Credentials = authorization.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			// Credentials = username:password
			String[] values = credentials.split(":", 2);

			// Atributos com valores para comparação:
			// String customHeader = "162100594";
			// String login = "adail101@hotmail.com";
			// String senha = "@Hacker101";

			// Autenticação dos valores de atributos para Uplinks:
			if (customHeader.equals(valorCustomHeader) && login.equals(values[0]) && senha.equals(values[1])) {
				prosseguir = true;
				System.out.println("REQUISIÇÃO AUTENTICADA!");
				System.out.println("Credenciais decodificadas:");
				System.out.println(credentials);
				System.out.println("Login: " + login);
				System.out.println("Senha: " + senha);

			} else {
				prosseguir = false;
				// System.err.println("Erro de Autenticação, a requisição não pode
				// prosseguir!");
			}

		}

		return prosseguir;

	}

	public void preHandleSemObjectHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("========================================================");
		System.out.println("====================== REQUISIÇÃO ======================");
		System.out.println("========================================================");

		// Método usado para REQUISIÇÃO:
		System.out.println("Requisição do Tipo: " + request.getMethod());
		System.out.println("URI do Recurso: " + request.getRequestURI());
		System.out.println("URL da Requisição: " + request.getRequestURL());

		// Cabeçalhos da REQUISIÇÃO:
		Enumeration<String> headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {

			String headerName = headerNames.nextElement();
			System.out.println("Nome do cabeçalho: " + headerName + ", Valor: " + request.getHeader(headerName));
		}

		// Parâmetros de REQUISIÇÃO:
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {

			String paramName = params.nextElement();
			System.out.println("Nome do parâmetro: " + paramName + ", Valor: " + request.getParameter(paramName));

		}

		StringBuilder sb = new StringBuilder();

		sb.append("TIPO da Requisição = [" + request.getMethod() + "], ");
		sb.append("URL da Requisição = [" + request.getRequestURL() + "], ");

		String headersRequest = Collections.list(request.getHeaderNames()).stream()
				.map(headerName -> headerName + " : " + Collections.list(request.getHeaders(headerName)))
				.collect(Collectors.joining(", "));

		if (headersRequest.isEmpty()) {
			sb.append("Cabeçalho da Requisição: NENHUM,");
		} else {
			sb.append("Cabeçalho da Requisição: [" + headersRequest + "],");
		}

		String parameters = Collections.list(request.getParameterNames()).stream()
				.map(p -> p + " : " + Arrays.asList(request.getParameterValues(p))).collect(Collectors.joining(",\n"));

		if (parameters.isEmpty()) {
			sb.append("Parâmetros da Requisição: NENHUM.");
		} else {
			sb.append("Parâmetros da Requisição: [" + parameters + "].");
		}

		System.out.println("========================================================");
		System.out.println("======================= RESPOSTA =======================");
		System.out.println("========================================================");

		logDetalhesResposta(response);

		logResponseHttp(request, response);
	}

	// REQUISIÇÃO (Sem utilização)
	// Log Simples (no Console):
	public String logRequestHttp(HttpServletRequest request, HttpServletResponse response, Model model)
			throws IOException {

		// Cabeçalhos:
		Enumeration<String> headerNames = request.getHeaderNames();
		List<String> lHeaderN = new ArrayList<String>();
		String headerNamesForHtml = new String();

		while (headerNames.hasMoreElements()) {
			String HeaderName = (String) headerNames.nextElement();
			lHeaderN.add(HeaderName + " = " + request.getHeader(HeaderName));
			headerNamesForHtml += (String) headerNames.nextElement() + " = " + request.getHeader(HeaderName) + "<br/>";
		}
		model.addAttribute("header", headerNamesForHtml);
		System.out.println(lHeaderN.toString());

		return "Retorno OK, dados no Console.";
	}

	// RESPONSE:
	// Apenas StatusCode e Headers (na Resposta):
	public String logResponseHttp(HttpServletRequest request, HttpServletResponse response) {

		StringBuilder sb = new StringBuilder();

		sb.append("Código de status da Resposta = [" + response.getStatus() + "].\n");
		String headersResponse = response.getHeaderNames().stream()
				.map(headerName -> headerName + " : " + response.getHeaders(headerName))
				.collect(Collectors.joining(",\n"));

		if (headersResponse.isEmpty()) {
			sb.append("Nenhum RESPONSE HEADERS,");
		} else {
			sb.append("RESPONSE HEADERS:\n" + headersResponse + ".");
		}

		System.out.println(sb.toString());

		return sb.toString();
	}

	public void logDetalhesRequisicao(HttpServletRequest request) throws Exception {

		System.out.println();
		System.out.println("================ DETALHES DA REQUISIÇÃO ================");

		String authType = request.getAuthType();
		System.out.println("AuthType: " + authType);

		String characterEncoding = request.getCharacterEncoding();
		System.out.println("CharacterEncoding: " + characterEncoding);

		String contentType = request.getContentType();
		System.out.println("ContentType: " + contentType);

		String contextPath = request.getContextPath();
		System.out.println("ContextPath: " + contextPath);

		String headerAuthorization = request.getHeader("Authorization");
		System.out.println("Valor do Header Authorization: " + headerAuthorization);

		String headerFacid = request.getHeader("Facid");
		System.out.println("Valor do Header Facid: " + headerFacid);

		String localAddr = request.getLocalAddr();
		System.out.println("LocalAddr: " + localAddr);

		String localName = request.getLocalName();
		System.out.println("LocalName: " + localName);

		String method = request.getMethod();
		System.out.println("Method: " + method);

		String parameter = request.getParameter(""); // TODO: Procurar por um parâmentro de requisição.
		System.out.println("Parameter: " + parameter);

		String pathInfo = request.getPathInfo();
		System.out.println("PathInfo: " + pathInfo);

		String pathTranslated = request.getPathTranslated();
		System.out.println("PathTranslated: " + pathTranslated);

		String protocol = request.getProtocol();
		System.out.println("Protocol: " + protocol);

		String queryString = request.getQueryString();
		System.out.println("QueryString: " + queryString);

		@SuppressWarnings("deprecation")
		String realPath = request.getRealPath(""); // TODO: Procurar por um path de requisição.
		System.out.println("RealPath: " + realPath);

		String remoteAddr = request.getRemoteAddr();
		System.out.println("RemoteAddr: " + remoteAddr);

		String remoteHost = request.getRemoteHost();
		System.out.println("RemoteHost: " + remoteHost);

		String remoteUser = request.getRemoteUser();
		System.out.println("RemoteUser: " + remoteUser);

		String requestedSessionId = request.getRequestedSessionId();
		System.out.println("RequestedSessionId: " + requestedSessionId);

		String requestURI = request.getRequestURI();
		System.out.println("RequestURI: " + requestURI);

		String scheme = request.getScheme();
		System.out.println("Scheme: " + scheme);

		String serverName = request.getServerName();
		System.out.println("ServerName: " + serverName);

		// String servletPath = request.getServletPath();
		// System.out.println("ServletPath: " + servletPath);

		// AsyncContext asyncContext = request.getAsyncContext();
		// System.out.println("AsyncContext: " + asyncContext);

		Object attribute = request.getAttribute("");
		System.out.println("Attribute: " + attribute);

		Enumeration<String> attributeNames = request.getAttributeNames();
		System.out.println("AttributeNames: " + attributeNames);

		Object _class = request.getClass();
		System.out.println("Class: " + _class);

		int contentLength = request.getContentLength();
		System.out.println("ContentLength: " + contentLength);

		Long contentLengthLong = request.getContentLengthLong();
		System.out.println("ContentLengthLong: " + contentLengthLong);

		Cookie[] cookies = request.getCookies();
		System.out.println("Cookies: " + cookies);

		Long dateHeader = request.getDateHeader(""); // TODO: Encontrar um Header para Ser analisado aqui.
		System.out.println("DateHeader: " + dateHeader);

		DispatcherType dispatcherType = request.getDispatcherType();
		System.out.println("DispatcherType: " + dispatcherType);

		Enumeration<String> headerNames = request.getHeaderNames();
		System.out.println("HeaderNames: " + headerNames);

		Enumeration<String> headers = request.getHeaders(""); // TODO: Encontrar um Header para Ser analisado aqui.
		System.out.println("Headers: " + headers);

		ServletInputStream inputStream = request.getInputStream();
		System.out.println("InputStream: " + inputStream);

		int intHeader = request.getIntHeader("Facid");
		System.out.println("IntHeader \"Facid\": " + intHeader);

		Locale locale = request.getLocale();
		System.out.println("Locale: " + locale);

		Enumeration<Locale> locales = request.getLocales();
		System.out.println("Locales: " + locales);

		int localPort = request.getLocalPort();
		System.out.println("LocalPort: " + localPort);

		Map<String, String[]> parameterMap = request.getParameterMap();
		System.out.println("ParameterMap: " + parameterMap);

		Enumeration<String> parameterNames = request.getParameterNames();
		System.out.println("ParameterNames: " + parameterNames);

		String[] parameterValues = request.getParameterValues(""); // TODO: Encontrar um valores de parâmetros.
		System.out.println("ParameterValues: " + parameterValues);

		// Part part = request.getPart(""); // TODO: Encontrar um Part.
		// System.out.println("Part: " + part);

		// Collection<Part> parts = request.getParts();
		// System.out.println("Parts: " + parts);

		// BufferedReader reader = request.getReader();
		// System.out.println("Reader: " + reader);

		int remotePort = request.getRemotePort();
		System.out.println("RemotePort: " + remotePort);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); // TODO: Encontrar um path.
		System.out.println("RequestDispatcher: " + requestDispatcher);

		StringBuffer requestURL = request.getRequestURL();
		System.out.println("RequestURL: " + requestURL);

		int serverPort = request.getServerPort();
		System.out.println("ServerPort: " + serverPort);

		ServletContext servletContext = request.getServletContext();
		System.out.println("ServletContext: " + servletContext);

		HttpSession session = request.getSession();
		System.out.println("Session: " + session);

		HttpSession booleanSession = request.getSession(true); // TODO: true or false.
		System.out.println("BooleanSession: " + booleanSession);

		Principal userPrincipal = request.getUserPrincipal();
		System.out.println("UserPrincipal: " + userPrincipal);

		System.out.println("============== FIM DETALHES DA REQUISIÇÃO ==============");
		System.out.println();
	}

	public void logDetalhesResposta(HttpServletResponse response) throws Exception {

		System.out.println();
		System.out.println("================= DETALHES DA RESPOSTA =================");

		String characterEncoding = response.getCharacterEncoding();
		System.out.println("CharacterEncoding: " + characterEncoding);

		String contentType = response.getContentType();
		System.out.println("ContentType: " + contentType);

		String headerAccessControlAllowOrigin = response.getHeader("Access-Control-Allow-Origin");
		System.out.println("Header Access-Control-Allow-Origin: " + headerAccessControlAllowOrigin);

		String headerAccessControlAllowCredentials = response.getHeader("Access-Control-Allow-Credentials");
		System.out.println("Header Access-Control-Allow-Credentials: " + headerAccessControlAllowCredentials);

		String headerSetCookie = response.getHeader("Set-Cookie");
		System.out.println("Header Set-Cookie: " + headerSetCookie);

		int bufferSize = response.getBufferSize();
		System.out.println("BufferSize: " + bufferSize);

		Class<? extends HttpServletResponse> _class = response.getClass();
		System.out.println("Classe: " + _class);

		Collection<String> headerNames = response.getHeaderNames();
		System.out.println("HeaderNames: " + headerNames);

		Collection<String> headers = response.getHeaders("");
		System.out.println("Headers: " + headers);

		Locale locale = response.getLocale();
		System.out.println("Locale: " + locale);

		// ServletOutputStream outputStream = response.getOutputStream();
		// System.out.println("outputStream: " + outputStream);

		int status = response.getStatus();
		System.out.println("Status: " + status);

		// PrintWriter writer = response.getWriter();
		// System.out.println("Writer: " + writer);

		System.out.println("=============== FIM DETALHES DA RESPOSTA ===============");
		System.out.println();

	}

	/*
	 * // CORS (CorsFilter):
	 * 
	 * // TODO: Configurar para diferentes ambientes. private String originPermitida
	 * = "http://localhost:8000"; // Após implementação da OriginPermitida em
	 * "TccFacidApiProperty.class"
	 * 
	 * // @Autowired // private TccFacidApiProperty tccFacidApiProperty;
	 * 
	 * @Override public void init(FilterConfig filterConfig) throws ServletException
	 * {
	 * 
	 * }
	 * 
	 * @Override public void doFilter(ServletRequest req, ServletResponse resp,
	 * FilterChain chain) throws IOException, ServletException {
	 * 
	 * // Conversão do Servlet request para HttpServletResquet e HttpServletResponse
	 * HttpServletRequest request = (HttpServletRequest) req; // Cast entre tipos.
	 * HttpServletResponse response = (HttpServletResponse) resp; // Cast entre
	 * tipos.
	 * 
	 * // Outros Headers importantes: // ESTÃO FORA DO OPTIONS POIS SEMPRE TERÃO DE
	 * SER ENVIADOS. // Para que outros funcionem como POST, GET, PUT, DELETE... //
	 * response.setHeader("Access-Control-Allow-Origin",
	 * tccFacidApiProperty.getOriginPermitida());
	 * 
	 * response.setHeader("Access-Control-Allow-Origin", originPermitida);
	 * response.setHeader("Access-Control-Allow-Credentials", "true");
	 * 
	 * // " "Access-Control-Allow-Credentials", "true" // por conta do cookie do
	 * refresh token. assim o cookie é enviado.
	 * 
	 * // Se a Requisição for um "OPTIONS" permite a "pre failed request". // Se não
	 * continua com o filtro normal. // Se for o OPTIONS não pode deixar continuar
	 * pois o Spring Security irá // bloquear a requisição. // Também verificar se a
	 * origem permitida é a mesma origem da requisição.
	 * 
	 * System.out.println("=== FILTRO CORS ==="); if
	 * ("OPTIONS".equals(request.getMethod()) &&
	 * originPermitida.equals(request.getHeader("Origin"))) {
	 * 
	 * System.out.println("Método da Requisição: " + request.getMethod());
	 * System.out.println("Header da Requisição é Origin e o seu valor é: " +
	 * request.getHeader("Origin"));
	 * 
	 * // Após implementação da OriginPermitida em "TccFacidApiProperty.class" // if
	 * ("OPTIONS".equals(request.getMethod()) // &&
	 * tccFacidApiProperty.getOriginPermitida().equals(request.getHeader("Origin")))
	 * { // response2.setHeader(name, value); // 1º Métodos HTTP Suportados. // 2º
	 * Headers HTTP Suportados. // 3º Tempo que o Browser irá fazer a próxima
	 * requisição. // em cache "3600" SEGUNDOS, ou seja UMA HORA. // RESUMO: O CORS
	 * adiciona estes Headers HTTP. // Header de resposta começam com
	 * "Access-Control", aqui a verificação se resume // em constatar se origem
	 * permitida é igual a origem que veio do browser e for // uma requisição
	 * "OPTIONS", acrescentado é este Headers e devolve "status OK" // para a
	 * requisição posterior poder ser concluída. // Se não for Bloqueia e a police
	 * do JavaScript não permitirá ir a frente.
	 * 
	 * response.setHeader("Access-Control-Allow-Methods",
	 * "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
	 * response.setHeader("Access-Control-Allow-Headers",
	 * "Authorization, Content-Type, Accept");
	 * response.setHeader("Access-Control-Max-Age", "3600");
	 * 
	 * // Se OPTIONS e Configurações aceitas, Resposta OK:
	 * response.setStatus(HttpServletResponse.SC_OK);
	 * System.out.println("Fim do Filtro Cross-origin resource sharing (CORS)."); }
	 * else {
	 * 
	 * // Continuar a cadeia do Filtro (Fluxo da requisição): chain.doFilter(req,
	 * resp); }
	 * 
	 * }
	 * 
	 * @Override public void destroy() {
	 * 
	 * }
	 */
}
