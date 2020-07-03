package com.adailsilva.tcc.facid.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

// Pesquisa de Lançamento com o Metamodel:
public class LancamentoFilter {

	@Getter
	@Setter
	private String descricao;

	@Getter
	@Setter
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoDe;

	@Getter
	@Setter
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoAte;

}
