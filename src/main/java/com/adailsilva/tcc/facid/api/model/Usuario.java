package com.adailsilva.tcc.facid.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "Usuario")
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1605070021026173888L;

	@Id // Não é "AUTO_INCREMENT" no Banco.
	@Getter
	@Setter
	@Column(name = "id")
	private Long id;

	@Getter
	@Setter
	@Column(name = "nome")
	private String nome;

	@Getter
	@Setter
	@Column(name = "email") // TODO: Trocar esse parâmentro totalmente por login.
	private String email;

	@Getter
	@Setter
	@Column(name = "senha")
	private String senha;

	// Relacionamento Muitos para Muitos (ficou só deste lado: Usuário -> Permissão)
	// "fetch = FetchType.EAGER"
	// Sempre que pegar o usuários já virão com as suas permissões.
	// "id_usuario" que contem o relacionamento com permissão
	// "id_permissao" o contrário.
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "permissao_usuario", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_permissao"))
	@Getter
	@Setter
	private List<Permissao> permissoes;

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", permissoes="
				+ permissoes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((permissoes == null) ? 0 : permissoes.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
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
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (permissoes == null) {
			if (other.permissoes != null)
				return false;
		} else if (!permissoes.equals(other.permissoes))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}

}
