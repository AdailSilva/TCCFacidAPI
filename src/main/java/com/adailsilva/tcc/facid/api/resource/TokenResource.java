package com.adailsilva.tcc.facid.api.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.adailsilva.tcc.facid.api.config.property.TccFacidApiProperty;

// Esta classe é responsável por "Logout":
// JWT com ele não se tem o armazenamento do token em lugar algum.
// o bom pra escalar o Serviço REST para muitos servidores.
// Por outro lado não tem como invalidar o token (Logout).
// ENTÃO:
// Apagar o "AccessToken" da memória usada pelo Cliente (Browser)
// e chamar no servidor um método para apagar o "RefreshToken" do Cookie no cliente (Browser).
@RestController
@RequestMapping("/tokens")
public class TokenResource {
	
	// Cookie Seguro (Apenas em HTTPS) sim ou não.
	// Para Desenvolvimento false e em PRODUÇÃO true.
//	@Autowired
//	private TccFacidApiProperty TccFacidApiProperty; 

	// Apagando o COOKIE:
	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {

		// APAGANDO O COOKIE:

		// Cookie de nome: "refreshToken" e conteúdo nulo.
		Cookie cookie = new Cookie("refreshToken", null);

		// Acessível apenas em HTTP.
		cookie.setHttpOnly(true);

		// Cookie Seguro (Apenas em HTTPS) sim ou não.
		// Para Desenvolvimento false e em PRODUÇÃO true.
		// cookie.setSecure(false); // TODO: Mudar para true em producao.
//		cookie.setSecure(TccFacidApiProperty.getSeguranca().isEnableHttps());

		// Usando aqui a REQUISIÇÃO:
		// Caminho que esse Cookie deve ser enviado pelo browser automaticamente.
		cookie.setPath(req.getContextPath() + "/oauth/token");

		// Validade do Cookie "0" == 0 dias.
		cookie.setMaxAge(0);

		resp.addCookie(cookie);
		// Já setando a resposta de Status HTTP com o HttpServlet:
		resp.setStatus(HttpStatus.NO_CONTENT.value()); // Status HTTP: 204.
	}

}

// RESUMO:
/*
 * Como não se tem acesso ao "access token", o logout tem que ser feito pela
 * remoção do "refresh token":
 *
 * A ideia é: remover do cliente o acesso ao refresh token, assim ele não vai
 * conseguir mais pedir um "access token" a partir de um "refresh token".
 * 
 * A URU "/revoke" faz exatamente isso. Uma aplicação cliente não vai mais
 * conseguir acessar o "refresh token", logo não vai mais conseguir faze
 * requisições válidas.
 * 
 * O restante da aplicação não muda. Apenas adicionamos o "/revoke" e suas
 * regras de exclusão de token.
 * 
 * Quando é feita uma nova requisição para "/oauth/token" o usuário está
 * praticamente fazendo um "novo login", pois irá ter um novo "Acess Token" e
 * outro novo "Refresh Token" será colocado no Cookie. (vazio)
 * 
 * A responsabilidade da nossa aplicação do backend é apenas retirar este token
 * da aplicação cliente.
 * 
 * A aplicação Cliente, quando o usuário clicar em logout, irá remover o "Access
 * Token", logo após isso, a aplicação REST irá remover o "Refresh Token" do
 * Cookie, logo o cliente não terá acesso. Então a aplicação cliente fica sem
 * "Access Token" e sem "Refresh token" para recuperar um novo "Access Token".
 * 
 * Este passo de Logout estará completo, quando implementarmos o logout na
 * aplicação Angular. Mas a responsabilidade da Aplicação REST já está pronta,
 * removendo o "Refresh Token".
 */