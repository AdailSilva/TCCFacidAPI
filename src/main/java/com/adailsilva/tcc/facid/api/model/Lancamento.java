package com.adailsilva.tcc.facid.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

// import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "Lancamento")
@Table(name = "lancamentos")
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 1991314973331296600L;

	@Id
	// A tabela que toma conta do preenchimento dos ids:
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	@Column(name = "id")
	private Long id;

	// anotações do BeanValidation (já incluso no POM em:
	// spring-boot-starter-data-jpa)
	@NotNull
	@Size(min = 3, max = 50)
	@Getter
	@Setter
	@Column(name = "descricao")
	private String descricao;

	// anotações do BeanValidation (já incluso no POM em:
	// spring-boot-starter-data-jpa)
	@NotNull
	@Getter
	@Setter
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@Getter
	@Setter
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	// anotações do BeanValidation (já incluso no POM em:
	// spring-boot-starter-data-jpa)
	@NotNull
	@Getter
	@Setter
	@Column(name = "valor")
	private BigDecimal valor;

	@Getter
	@Setter
	@Column(name = "observacao")
	private String observacao;

	// anotações do BeanValidation (já incluso no POM em:
	// spring-boot-starter-data-jpa)
	@NotNull
	@Enumerated(EnumType.STRING) // Há dois tipos ORDINAL e STRING, Ordinal é posição do ENUM.
	@Getter
	@Setter
	@Column(name = "tipo")
	private TipoLancamento tipo;

	// RELACIONAMENTOS:
	// anotações do BeanValidation (já incluso no POM em:
	// spring-boot-starter-data-jpa)
	@NotNull
	// Com cascade = CascadeType.ALL estou conseguindo alterações tanto no objeto
	// principal quanto em Objetos relacionados.
	// Se no Relacionamento coloco "CascadeType.ALL" só salva com todos os
	// parâmentros dos objetos
	// filhos do relacionamento. Porém altera tudo até os filhos nos updates.
	// (cascade = CascadeType.ALL) // Hum lançamento pode ter uma única categoria,
	// já uma categoria vários lançamentos.
	@ManyToOne
	@JoinColumn(name = "id_categoria") // Coluna que faz o relacionamento.
	@Getter
	@Setter
	private Categoria categoria;

	// anotações do BeanValidation (já incluso no POM em:
	// spring-boot-starter-data-jpa)
	@NotNull
	// Com cascade = CascadeType.ALL estou conseguindo alterações tanto no objeto
	// principal quanto em Objetos relacionados.
	// Se no Relacionamento coloco "CascadeType.ALL" só salva com todos os
	// parâmentros dos objetos
	// filhos do relacionamento. Porém altera tudo até os filhos nos updates.
	// (cascade = CascadeType.ALL) // Hum lançamento pode ter uma única pessoa,
	// já uma pessoa vários lançamentos.
	@ManyToOne
	@JoinColumn(name = "id_pessoa") // Coluna que faz o relacionamento.
	@Getter
	@Setter
	private Pessoa pessoa;

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", descricao=" + descricao + ", dataVencimento=" + dataVencimento
				+ ", dataPagamento=" + dataPagamento + ", valor=" + valor + ", observacao=" + observacao + ", tipo="
				+ tipo + ", categoria=" + categoria + ", pessoa=" + pessoa + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((dataPagamento == null) ? 0 : dataPagamento.hashCode());
		result = prime * result + ((dataVencimento == null) ? 0 : dataVencimento.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (dataPagamento == null) {
			if (other.dataPagamento != null)
				return false;
		} else if (!dataPagamento.equals(other.dataPagamento))
			return false;
		if (dataVencimento == null) {
			if (other.dataVencimento != null)
				return false;
		} else if (!dataVencimento.equals(other.dataVencimento))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		if (tipo != other.tipo)
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

}
