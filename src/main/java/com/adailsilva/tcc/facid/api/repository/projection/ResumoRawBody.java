package com.adailsilva.tcc.facid.api.repository.projection;

import lombok.Getter;
import lombok.Setter;

// PROJEÇÃO DE RAWBODY:
public class ResumoRawBody {

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String app_id;

	// Construtor:
	public ResumoRawBody(Long id, String app_id) {
		super();
		this.id = id;
		this.app_id = app_id;
	}

}
