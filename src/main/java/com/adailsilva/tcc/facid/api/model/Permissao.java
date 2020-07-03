package com.adailsilva.tcc.facid.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "Permissao")
@Table(name = "permissoes")
public class Permissao implements Serializable {

	private static final long serialVersionUID = -8243100663713924596L;

	@Id // Não é "AUTO_INCREMENT" no Banco.
	@Getter
	@Setter
	@Column(name = "id")
	private Long id;

	@Getter
	@Setter
	@Column(name = "descricao")
	private String descricao;

	@Override
	public String toString() {
		return "Permissao [id=" + id + ", descricao=" + descricao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Permissao other = (Permissao) obj;
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
		return true;
	}

	
}
