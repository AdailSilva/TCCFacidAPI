package com.adailsilva.tcc.facid.api.repository.projection;

import lombok.Getter;
import lombok.Setter;

// PROJEÇÃO DE GATEWAY:
public class ResumoGateway {

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String gtw_id;

	// Construtor:
	public ResumoGateway(Long id, String gtw_id) {
		super();
		this.id = id;
		this.gtw_id = gtw_id;
	}

}
