package com.adailsilva.tcc.facid.api.config;
/*
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// PARA UTILIZAR esta classe comentar além dos demais tipos de segurança
// do pacote config, comentar também todo o pacote "token" e "security".
// Segurança SEM OAuth ( EM MEMÓRIA )
// Já vem como padrão a anotação "@Configuration", porém vou colocar.
// Anotação "@EnableWebSecurity" para ativar a segurança.
@Configuration
@EnableWebSecurity
public class SecurityConfigHttpBasic extends WebSecurityConfigurerAdapter {
	
	// Método de AUTENTICAÇÃO:
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// AUTENTICAÇÃO EM MEMÓRIA:
		auth.inMemoryAuthentication().withUser("admin").password("Hacker101").roles("TTN");
		// super.configure(auth); // Se não comentar dá erro de Autenticação!
	}

	// Método de AUTORIZAÇÃO:
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/rawbodies/uplinks").permitAll() // Qualquer um pode acessar.
				.anyRequest().authenticated() // o restante das requisições precisam de autenticação.
				.and().httpBasic() // tipo de autenticação Básica.
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sem estado.
				.and().csrf().disable(); // É uma proteção contra Java Script Injection na parte Web.
		// super.configure(http); // Esse pode Ficar não dá erro.
	}
}
*/