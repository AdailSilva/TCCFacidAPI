package com.adailsilva.tcc.facid.api.repository.projection;

import lombok.Getter;
import lombok.Setter;

// PROJEÇÃO DE PAYLOADFIELD:
public class ResumoPayloadField {

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String text;

	// Construtor:
	public ResumoPayloadField(Long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

}
