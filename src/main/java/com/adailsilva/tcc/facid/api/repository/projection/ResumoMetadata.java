package com.adailsilva.tcc.facid.api.repository.projection;

import lombok.Getter;
import lombok.Setter;

// PROJEÇÃO DE METADATA:
public class ResumoMetadata {

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String modulation;

	// Construtor:
	public ResumoMetadata(Long id, String modulation) {
		super();
		this.id = id;
		this.modulation = modulation;
	}

}
