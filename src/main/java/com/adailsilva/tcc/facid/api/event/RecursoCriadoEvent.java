package com.adailsilva.tcc.facid.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
// import lombok.Setter;

// Classe para retirar a duplicação dos Eventos.
public class RecursoCriadoEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;

	@Getter
	// @Setter
	private HttpServletResponse response;

	@Getter
	// @Setter
	private Long id;

	// Construtor
	// Quando for adicionar o HeaderLocation, tenho que criar este evento, passando
	// o ServletResponse e o Código.
	public RecursoCriadoEvent(Object source, HttpServletResponse response, Long id) {
		super(source);

		this.response = response;
		this.id = id;
	}

}
