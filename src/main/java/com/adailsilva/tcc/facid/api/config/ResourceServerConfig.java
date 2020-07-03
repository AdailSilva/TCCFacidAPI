package com.adailsilva.tcc.facid.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

// Segurança com OAuth2 ( RESOURCE SERVER )
// Grant Type: Resource Owner Password Flow ( PASSWORD FLOW )

// Já vem como padrão a anotação "@Configuration", porém vou colocar.
// Anotação "@EnableWebSecurity" para ativar a segurança.
@Configuration
@EnableWebSecurity
@EnableResourceServer // Para OAuth funcionar
// 6.12. Adicionando permissões de acesso
// Para habilitar a segurança nos métodos
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// AUTENTICAÇÃO EM MEMÓRIA:
		// Permissão "ROLE" para Autorização.
		// "User" e "Password" para Autenticação.
		// REFATORAÇÃO "6.11. Movendo o usuário para o banco de dados"
		// auth.inMemoryAuthentication().withUser("admin").password("Hacker101").roles("TTN");

		// auth.userDetailsService(userDetailsService); // Injetado acima.
		// Informando o Encoder do Password para o Spring:
		// auth.userDetailsService(userDetailsService);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		// Esta parte não é obrigatória.
		// super.configure(auth); // Sem comentar dá erro de Autenticação!
	}

	// Além de ser sobrescrito tem que ser deixado agora como um método Público.
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		// POIS "@PreAuthorize("hasAuthority('ROLE_CADASTRAR_RAWBODY')")" está ativo.
		httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/rawbodies/uplinks").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/categorias").permitAll();
		// httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/rawbodies/uplinks").authenticated();
		// httpSecurity.authorizeRequests().antMatchers("/rawbodies/uplinks").authenticated().and().httpBasic();

		httpSecurity.authorizeRequests().anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sem estado.
				.and().csrf().disable(); // Disabilitando para proteção por exemplo um JavaScript Injection.
		// super.configure(httpSecurity); // Esse pode Ficar não dá erro.
	}

	// Sem estado. (STATELESS)
	@Override
	public void configure(ResourceServerSecurityConfigurer resourcesServerSecurityConfigurer) throws Exception {
		resourcesServerSecurityConfigurer.stateless(true); // Sem estado.
		// Poderia retirar o "super.configure(resources);"
		// super.configure(resources);
	}

	// Caso dê erro de "checksum" acrescentar anotação @Bean.
	// Para reconhecimento de Senhas encodadas.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 6.12. Adicionando permissões de acesso
	// Para habilitar a segurança nos métodos
	// Após acrescentar: @EnableGlobalMethodSecurity(prePostEnabled = true)
	// Acrescentar este Bean para funcionar com o "OAuth2"
	// "OAuth2MethodSecurityExpressionHandler()" para ser possível funcionar esta
	// Segurança com o "OAuth2".
	@Bean
	public MethodSecurityExpressionHandler createExpressionHandler() {
		return new OAuth2MethodSecurityExpressionHandler();
	}
}
