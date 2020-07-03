package com.adailsilva.tcc.facid.api.repository.projection;

import lombok.Getter;
import lombok.Setter;

// PROJEÇÃO DE CATEGORIA:
public class ResumoCategoria {

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String nome;

	// Construtor:
	public ResumoCategoria(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

}
