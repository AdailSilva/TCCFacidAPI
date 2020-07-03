package com.adailsilva.tcc.facid.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "PayloadField")
@Table(name = "payload_fields")
public class PayloadField implements Serializable {

	private static final long serialVersionUID = -7735387995047762012L;

	@Id
	// A tabela que toma conta do preenchimento dos ids:
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Size(min = 1, max = 50)
	@Getter
	@Setter
	@Column(name = "id")
	private Long id;

	// @NotNull
	@Size(min = 0, max = 50)
	@Getter
	@Setter
	@Column(name = "text")
	private String text;

	// RELACIONAMENTO: (PayloadField -> RawBody)
	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name = "raw_bodies_id")
	@JsonIgnore
	private RawBody rawBody;

	@Override
	public String toString() {
		return "PayloadField [id=" + id + ", text=" + text + ", rawBody=" + rawBody + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rawBody == null) ? 0 : rawBody.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		PayloadField other = (PayloadField) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rawBody == null) {
			if (other.rawBody != null)
				return false;
		} else if (!rawBody.equals(other.rawBody))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}