package com.adailsilva.tcc.facid.api.security.jwt;
/*
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
	
		httpSecurity.csrf().disable().authorizeRequests()
				// .antMatchers("/rawbodies/uplinks").permitAll()
				// .antMatchers(HttpMethod.POST, "/rawbodies/uplinks").permitAll()
				// .anyRequest().authenticated()
				// .antMatchers(HttpMethod.POST, "/rawbodies/uplinks").authenticated()
				// .and()

				// Filtra requisições de login.
				// .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
				// UsernamePasswordAuthenticationFilter.class)

				// Filtra outras requisições para verificar a presença do JWT no header.
				// .addFilterBefore(new JWTAuthenticationFilter(),
				// UsernamePasswordAuthenticationFilter.class);

				.antMatchers(HttpMethod.POST, "/rawbodies/uplinks").authenticated();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		// Cria uma conta default:
		auth.inMemoryAuthentication().withUser("adailsilva").password("Hacker101").roles("ADMIN");
	}
}
*/