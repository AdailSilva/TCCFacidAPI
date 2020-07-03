package com.adailsilva.tcc.facid.api.repository.projection;

import lombok.Getter;
import lombok.Setter;

// PROJEÇÃO DE PESSOA:
public class ResumoPessoa {

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String nome;

	// Construtor:
	public ResumoPessoa(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

}
