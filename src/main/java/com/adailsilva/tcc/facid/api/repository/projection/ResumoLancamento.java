package com.adailsilva.tcc.facid.api.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.adailsilva.tcc.facid.api.model.TipoLancamento;

import lombok.Getter;
import lombok.Setter;

// PROJEÇÃO DE LANÇAMENTO:
public class ResumoLancamento {

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String descricao;

	@Getter
	@Setter
	private LocalDate dataVencimento;

	@Getter
	@Setter
	private LocalDate dataPagamento;

	@Getter
	@Setter
	private BigDecimal valor;

	// ATRIBUTO RETIRADO PARA SIMPLICAR LISTAGEM:
	// @Getter
	// @Setter
	// private String observacao;

	@Getter
	@Setter
	private TipoLancamento tipo;

	@Getter
	@Setter
	private String categoria; // Tipo aqui String Original Categoria.

	@Getter
	@Setter
	private String pessoa; // Tipo aqui String Original Pessoa.

	// Construtor:
	public ResumoLancamento(Long id, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
			BigDecimal valor, TipoLancamento tipo, String categoria, String pessoa) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;
	}

}
