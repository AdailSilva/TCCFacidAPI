package com.adailsilva.tcc.facid.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "Pessoa")
@Table(name = "pessoas")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 6838498663525860861L;

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
	@Column(name = "nome")
	private String nome;

	@Embedded // Embutida de uma outra classe que seja: "@Embeddable" Implantável.
	@Getter
	@Setter
	// @Column(name = "endereco")
	private Endereco endereco;

	@NotNull
	@Getter
	@Setter
	@Column(name = "ativo")
	private Boolean ativo;

	// Método adicionado para verificação de Pessoa inativa.
	// Necessário fazer com que o Jackson e Hibernate
	// ignore para não serializar ou buscar.
	// Não é uma propriedade para serializar.
	@JsonIgnore // Para Jackson Ignorar a propriedade.
	@Transient // Para Hibernate Ignorar a propriedade.
	public boolean isInativo() {
		return !this.ativo;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", ativo=" + ativo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (ativo == null) {
			if (other.ativo != null)
				return false;
		} else if (!ativo.equals(other.ativo))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
