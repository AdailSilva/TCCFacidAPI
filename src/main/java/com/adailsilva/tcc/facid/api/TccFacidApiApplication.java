package com.adailsilva.tcc.facid.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import com.adailsilva.tcc.facid.api.config.property.TccFacidApiProperty;

@SpringBootApplication
// Agora configurar no "application.properties"
// Ou seja foi habilitado para configurações externas.
// @EnableConfigurationProperties(TccFacidApiProperty.class)
public class TccFacidApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TccFacidApiApplication.class, args);
		
	}

	// CORS desativado Globalmente.

	// Configuração Global CORS:

	// @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// return new WebMvcConfigurerAdapter() {

	// @Override
	// public void addCorsMappings(CorsRegistry registry) {
	// registry.addMapping("/*").allowedOrigins("http://localhost:8000");
	// }
	// };
	// }

}
