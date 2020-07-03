package com.adailsilva.tcc.facid.api.security.jwt;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// Classe para criar e verificar tokens JWT:
// Nela vamos utilizar a classe que incluímos como dependência io.jsonwebtoken.Jwts para validar os tokens.
public class TokenAuthenticationService {

	// EXPIRATION_TIME = 10 dias
	static final long DATA_DE_VALIDADE = 860_000_000;
	static final String SEGREDO = "AdailSilva";
	static final String PREFIXO_TOKEN = "Bearer";
	static final String STRING_CABECALHO = "Authorization";

	static void addAutenticacao(HttpServletResponse response, String username) {
		
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + DATA_DE_VALIDADE))
				.signWith(SignatureAlgorithm.HS512, SEGREDO).compact();

		response.addHeader(STRING_CABECALHO, PREFIXO_TOKEN + " " + JWT);
	}

	static Authentication getAutenticacao(HttpServletRequest request) {
		
		String token = request.getHeader(STRING_CABECALHO);

		if (token != null) {
			// Faz parse do token:
			String usuario = Jwts.parser().setSigningKey(SEGREDO).parseClaimsJws(token.replace(PREFIXO_TOKEN, ""))
					.getBody().getSubject();

			if (usuario != null) {
				return new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());
			}
		}
		return null;
	}

}
