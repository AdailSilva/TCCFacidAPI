package com.adailsilva.tcc.facid.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "CustomHeader")
@Table(name = "custom_headers")
public class CustomHeader implements Serializable {

	private static final long serialVersionUID = 1143540565758172295L;

	@Id // Não é "AUTO_INCREMENT" no Banco.
	@Getter
	@Setter
	@Column(name = "id")
	private Long id;

	@Getter
	@Setter
	@Column(name = "custom_header")
	private String custom_header;

	@Getter
	@Setter
	@Column(name = "value_custom_header")
	private String value_custom_header;

	@Getter
	@Setter
	@Column(name = "login")
	private String login;

	@Getter
	@Setter
	@Column(name = "password")
	private String password;

	@Override
	public String toString() {
		return "CustomHeaders [id=" + id + ", custom_header=" + custom_header + ", value_custom_header="
				+ value_custom_header + ", login=" + login + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custom_header == null) ? 0 : custom_header.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((value_custom_header == null) ? 0 : value_custom_header.hashCode());
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
		CustomHeader other = (CustomHeader) obj;
		if (custom_header == null) {
			if (other.custom_header != null)
				return false;
		} else if (!custom_header.equals(other.custom_header))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (value_custom_header == null) {
			if (other.value_custom_header != null)
				return false;
		} else if (!value_custom_header.equals(other.value_custom_header))
			return false;
		return true;
	}

}
