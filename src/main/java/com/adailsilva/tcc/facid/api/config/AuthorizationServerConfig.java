package com.adailsilva.tcc.facid.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

// Segurança com OAuth2 ( AUTHORIZATION SERVER )
// Grant Type: Resource Owner Password Flow ( PASSWORD FLOW )

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// CLIENTE ANGULAR [ WEB ]
		// Autorizando o CLIENTE a usar o o AuthorizationServer:
		// clients.jdbc(dataSource); // poderia aqui levar para um DB com JDBC.
		clients.inMemory() // Autenticação em memória.
				.withClient("angular") // Cliente.
				.secret("senhaDoAngular") // Senha do Cliente.
				.scopes("create", "read", "update", "delete") // Limitando o acesso definindo o Scopo para o Clientes.
				// Serão usados posteriormente: .scopes("read", "write").
				// Fluxo PASSWORD FLOW para apps confiáveis, front tem acesso ao login.
				// .authorizedGrantTypes("password") // Usando o fluxo OAuth do tipo.
				// PasswordFlow.
				// ADICIONANDO REFRESH TOKEN:
				// REFRESH TOKEN será armazenado em um COOKIE seguro HTTPS.
				.authorizedGrantTypes("password", "refresh_token") // Usando o fluxo OAuth do tipo PasswordFlow.
				.accessTokenValiditySeconds(60) // Tempo para validade do Token.
				// Validade do REFRESH TOKEN:
				.refreshTokenValiditySeconds(3600 * 24)// ; // VALIDADE DE UM DIA.
				// poderia retirar o "super.configure(clients);"
				// super.configure(clients);
				.and()
				// CLIENTES iOS/ANDROID [ MOBILE ]
				// Autorizando o CLIENTE a usar o o AuthorizationServer:
				// clients.jdbc(dataSource); // poderia aqui levar para um DB com JDBC.
				// clients.inMemory() // Autenticação em memória.
				.withClient("mobile") // Cliente.
				.secret("senhaDoMobile") // Senha do Cliente.
				.scopes("read") // Limitando o acesso definindo o Scopo para o Clientes.
				// Fluxo PASSWORD FLOW apenas para apps confiáveis, front tem acesso ao login.
				// ADICIONANDO REFRESH TOKEN:
				// REFRESH TOKEN será armazenado em um COOKIE seguro HTTPS.
				.authorizedGrantTypes("password", "refresh_token") // Usando o fluxo OAuth do tipo PasswordFlow.
				// .accessTokenValiditySeconds(20); // Tempo para validade do Token.
				.accessTokenValiditySeconds(60) // Tempo para validade do Token.
				// Validade do REFRESH TOKEN:
				.refreshTokenValiditySeconds(3600 * 24)// ; // VALIDADE DE UM DIA.
				// poderia retirar o "super.configure(clients);"
				// super.configure(clients);
				.and()
				// CLIENTE SWING [ DESKTOP ]
				// Autorizando o CLIENTE a usar o o AuthorizationServer:
				// clients.jdbc(dataSource); // poderia aqui levar para um DB com JDBC.
				// clients.inMemory() // Autenticação em memória.
				.withClient("desktop") // Cliente.
				.secret("senhaDoDesktop") // Senha do Cliente.
				.scopes("read") // Limitando o acesso definindo o Scopo para o Clientes.
				// Serão usados posteriormente: .scopes("read", "write").
				// Fluxo PASSWORD FLOW apenas para apps confiáveis
				// pois o front tem acesso ao login.
				// Usando o fluxo OAuth do tipo PasswordFlow.
				// .authorizedGrantTypes("password")
				// ADICIONANDO REFRESH TOKEN:
				// REFRESH TOKEN será armazenado em um COOKIE seguro HTTPS.
				.authorizedGrantTypes("password", "refresh_token") // Usando o fluxo OAuth do tipo PasswordFlow.
				.accessTokenValiditySeconds(60) // Validade do Access Token.
				.refreshTokenValiditySeconds(3600 * 24); // Validade do Refresh Token.
		// poderia retirar o "super.configure(clients);"
		// super.configure(clients);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()) // Armazenamento do TOKEN no tokenStore().
				// Após refatoração para acrescentar o JWT
				// Adicionando um Conversor de TOKEN, "JwtAccessTokenConverter".
				.accessTokenConverter(jwtAccessTokenConverter())
				// APÓS REFATORAÇÃO ACRESCENTANDO O REFRESH TOKEN (.reuseRefreshTokens(false))
				// Sempre que pedir um AcessToken um novo Refresh Token será enviado.
				.reuseRefreshTokens(false) // não reaproveita o anterior.
				.authenticationManager(authenticationManager); // Validando com Usuário e Senha aqui.
		// poderia retirar o "super.configure(endpoints);"
		// super.configure(endpoints);
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() { // Acrescentado Jwt ao começo do Método
		// Acrescentar ao Token a chave que o valida.
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey("AdailSilva"); // Chave para o Token.
		// Não retornar o método "jwtAccessTokenConverter()" se não fica em Loop.
		return jwtAccessTokenConverter;
	}

	// "JwtTokenStore(accessTokenConverter()" não é armazenado em lugar algum.
	// Apenas uma forma de ter o "token store" apenas valida o token.
	@Bean
	public TokenStore tokenStore() {
		// Token em memória, Após refatoração usaremos JWT (Json Web Token)
		// não precisando ser armazenado desta maneira.
		// O "tokenStore" também será em memória após refatoração para usar o JWT.
		// return new InMemoryTokenStore();
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

}
