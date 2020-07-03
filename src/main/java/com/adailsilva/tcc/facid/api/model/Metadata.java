package com.adailsilva.tcc.facid.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "Metadata")
@Table(name = "metadata")
public class Metadata implements Serializable {

	private static final long serialVersionUID = -4851576151718505247L;

	@Id
	// A tabela que toma conta do preenchimento dos ids:
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Size(min = 1, max = 50)
	@Getter
	@Setter
	@Column(name = "id")
	private Long id;

	// @NotNull
	@Size(min = 1, max = 30)
	@Getter
	@Setter
	@Column(name = "time")
	private String time;

	// @NotNull
	// @Size(min = 1, max = 1)
	@Getter
	@Setter
	@Column(name = "frequency")
	private Float frequency;

	// @NotNull
	@Size(min = 1, max = 4)
	@Getter
	@Setter
	@Column(name = "modulation")
	private String modulation;

	// @NotNull
	@Size(min = 1, max = 9)
	@Getter
	@Setter
	@Column(name = "data_rate")
	private String data_rate;

	// @NotNull
	@Size(min = 1, max = 3)
	@Getter
	@Setter
	@Column(name = "coding_rate")
	private String coding_rate;

	// ATRIBUTOS ACRESCENTADOS PARA CONTROLE:
	// @NotNull
	// @Size(min = 1, max = 5)
	@Getter
	@Setter
	@Column(name = "bit_rate")
	private Integer bit_rate;

	// @NotNull
	// @Size(min = 1, max = 1)
	@Getter
	@Setter
	@Column(name = "latitude")
	private Float latitude;

	// @NotNull
	// @Size(min = 1, max = 1)
	@Getter
	@Setter
	@Column(name = "longitude")
	private Float longitude;

	// @NotNull
	// @Size(min = 1, max = 3)
	@Getter
	@Setter
	@Column(name = "altitude")
	private Integer altitude;

	// OBS: Não fazer toString(). Erro Stack Overflow.
	// RELACIONAMENTO: (Metadata -> RawBody)
	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name = "raw_bodies_id")
	@JsonIgnore
	private RawBody rawBody;

	// Feito toString() graças ao hashCode() total.
	// RELACIONAMENTO: (Metadata -> Gateway)
	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "metadata_id")
	private List<Gateway> gateways;

	@Override
	public String toString() {
		return "Metadata [id=" + id + ", time=" + time + ", frequency=" + frequency + ", modulation=" + modulation
				+ ", data_rate=" + data_rate + ", coding_rate=" + coding_rate + ", bit_rate=" + bit_rate + ", latitude="
				+ latitude + ", longitude=" + longitude + ", altitude=" + altitude + ", rawBody=" + rawBody
				+ ", gateways=" + gateways + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((altitude == null) ? 0 : altitude.hashCode());
		result = prime * result + ((bit_rate == null) ? 0 : bit_rate.hashCode());
		result = prime * result + ((coding_rate == null) ? 0 : coding_rate.hashCode());
		result = prime * result + ((data_rate == null) ? 0 : data_rate.hashCode());
		result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
		result = prime * result + ((gateways == null) ? 0 : gateways.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((modulation == null) ? 0 : modulation.hashCode());
		result = prime * result + ((rawBody == null) ? 0 : rawBody.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		Metadata other = (Metadata) obj;
		if (altitude == null) {
			if (other.altitude != null)
				return false;
		} else if (!altitude.equals(other.altitude))
			return false;
		if (bit_rate == null) {
			if (other.bit_rate != null)
				return false;
		} else if (!bit_rate.equals(other.bit_rate))
			return false;
		if (coding_rate == null) {
			if (other.coding_rate != null)
				return false;
		} else if (!coding_rate.equals(other.coding_rate))
			return false;
		if (data_rate == null) {
			if (other.data_rate != null)
				return false;
		} else if (!data_rate.equals(other.data_rate))
			return false;
		if (frequency == null) {
			if (other.frequency != null)
				return false;
		} else if (!frequency.equals(other.frequency))
			return false;
		if (gateways == null) {
			if (other.gateways != null)
				return false;
		} else if (!gateways.equals(other.gateways))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (modulation == null) {
			if (other.modulation != null)
				return false;
		} else if (!modulation.equals(other.modulation))
			return false;
		if (rawBody == null) {
			if (other.rawBody != null)
				return false;
		} else if (!rawBody.equals(other.rawBody))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

}