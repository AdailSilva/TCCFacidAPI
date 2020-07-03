package com.adailsilva.tcc.facid.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

// import com.adailsilva.tcc.facid.api.config.property.TccFacidApiProperty;

// CAPTURA DAS RESPOSTAS E FAZENDO ALTERAÇÕES:
// Classe para Retirar o "refresh_token" do Body da resposta da requisição e colocá-lo em um Cookie HTTPS.
// O JavaScript não terá acesso ao "refresh_token" por ele está em um Cookie.
// Interface "ResponseBodyAdvice<T>" pega a requisição e trabalha nela antes de devolvê-la para que a pediu.
// Exemplo "<T>" é o Tipo do dado a ser interceptado, neste caso o "OAuth2AccessToken".
// Poderia ser outro como o: "Categoria" por exemplo. interceptando as respostas dos controladores deste tipo de dado.
// Sempre que um corpo de resposta é o mesmo do tipo a ser tratado irá cair nos métodos desta classe.
// "OAuth2AccessToken" é o Tipo do dado que terá que ser interceptado em seu próprio retorno após requisição.
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {
	
	// Cookie Seguro (Apenas em HTTPS) sim ou não.
	// Para Desenvolvimento false e em PRODUÇÃO true.
	// retirado "T O D O" APÓS IMPLEMENTAÇÃO DA CLASSE: "TccFacidApiProperty.class"
//	@Autowired
//	private TccFacidApiProperty tccFacidApiProperty;
	
	// "supports()" mais um filtro para o método "beforeBodyWrite()".
	// só vai para ele se o retorno for true "supports()".
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

		// return false;
		// o tipo "OAuth2AccessToken" assim como outros pode ser retornados em vários
		// momentos do algoritmo, então:
		// só passa para o método "beforeBodyWrite" se aqui tiver um retorno de
		// "supports" true e para isso tem que retornar o método de nome:
		// "postAccessToken".
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		// (Cast) de "ServerHttpRequest e ServerHttpResponse"
		// para "HttpServletRequest e HttpServletResponse":
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
		// (Cast) de "OAuth2AccessToken body" para "DefaultOAuth2AccessToken body".
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		// Recuperando o Body da Requisição
		// Pegando o "refreshToken".
		String refreshToken = body.getRefreshToken().getValue();
		
		System.out.println();
		System.out.println("Refresh_Token: " + refreshToken);
		System.out.println();
		
		adicionarRefreshTokenNoCookie(refreshToken, req, resp);
		// Para "adicionarRefreshTokenNoCookie" preciso do HttpServletRequest e
		// HttpServletResponse.
		// já são recebidos porém como: "ServerHttpRequest request" e
		// "ServerHttpResponse response"
		removerRefreshTokenDoBody(token);

		// return null;
		return body;
	}

	// Criando o COOKIE:
	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		
		// Cookie de nome: "refreshToken" e conteúdo "refreshToken".
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken); // "refreshToken" Nome do Cookie.
		// Acessível apenas em HTTP.
		refreshTokenCookie.setHttpOnly(true);
		// Cookie Seguro (Apenas em HTTPS) sim ou não.
		// Para Desenvolvimento false e em PRODUÇÃO true.
		refreshTokenCookie.setSecure(false); // TODO: Mudar para true em produção.
		// retirado "T O D O" APÓS IMPLEMENTAÇÃO DA CLASSE: "TccFacidApiProperty.class"
//		refreshTokenCookie.setSecure(tccFacidApiProperty.getSeguranca().isEnableHttps());
		// Usando aqui a REQUISIÇÃO:
		// Caminho que esse Cookie deve ser enviado pelo browser automaticamente.
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
		// Validade do Cookie "2592000" == 30 dias.
		refreshTokenCookie.setMaxAge(2592000);
		// Usando aqui a RESPOSTA:
		// Adicionando o Cookie da Resposta:
		resp.addCookie(refreshTokenCookie);
	}
	
	// Remover o RefreshToken do Body:
	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		
		// "DefaultOAuth2AccessToken" tem um método "set"
		// Se faz necessário para retirada do body.
		token.setRefreshToken(null); // Está Null.
	}
}
