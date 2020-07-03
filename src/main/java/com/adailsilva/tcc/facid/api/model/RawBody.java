package com.adailsilva.tcc.facid.api.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "RawBody")
@Table(name = "raw_bodies")
public class RawBody implements Serializable {

	private static final long serialVersionUID = 3840635859585293013L;

	@Id
	// A tabela que toma conta do preenchimento dos ids:
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Size(min = 1, max = 50)
	@Getter
	@Setter
	@Column(name = "id")
	private Long id;

	@NotNull
	@Size(min = 2, max = 36)
	@Getter
	@Setter
	@Column(name = "app_id")
	private String app_id;

	@NotNull
	@Size(min = 2, max = 36)
	@Getter
	@Setter
	@Column(name = "dev_id")
	private String dev_id;

	// @NotNull
	@Size(min = 1, max = 16)
	@Getter
	@Setter
	@Column(name = "hardware_serial")
	private String hardware_serial;

	// @NotNull
	// @Size(min = 1, max = 3)
	@Getter
	@Setter
	@Column(name = "port") // Vai de 0 a 255.
	private Integer port;

	// @NotNull
	// @Size(min = 1, max = 50)
	@Getter
	@Setter
	@Column(name = "counter")
	private Integer counter;

	// @NotNull
	// @Size(min = 1, max = 100)
	@Getter
	@Setter
	@Column(name = "payload_raw")
	private String payload_raw;

	// @NotNull
	@Size(min = 1, max = 200)
	@Getter
	@Setter
	@Column(name = "downlink_url")
	private String downlink_url;

	// ATRIBUTOS ACRESCENTADOS PARA CONTROLE:
	// @NotNull
	// @Size(min = 1, max = 1)
	@Getter
	@Setter
	@Column(name = "is_retry")
	private Boolean is_retry;

	// @NotNull // No banco de dados é not null.
	// @Size(min = 1, max = 1)
	@Getter
	@Setter
	@Column(name = "confirmed")
	private Boolean confirmed;

	// @NotNull
	// @Size(min = 0, max = 7)
	@Getter
	@Setter
	@Column(name = "schedule")
	private String schedule;

	// @NotNull // No banco de dados é not null.
	// @Size(min = 1, max = 10)
	// @Getter
	// @Setter
	// @Column(name = "filter")
	// private String filter;

	// @NotNull // No banco de dados é not null.
	// @Size(min = 1, max = 10)
	@Getter
	@Setter
	@Column(name = "filter")
	@Enumerated(EnumType.STRING)
	private TipoPacote filter;

	// @NotNull
	@Size(min = 1, max = 19)
	@Getter
	@Setter
	@Column(name = "ack")
	private String ack;

	// @NotNull
	// @Size(min = 1, max = 3)
	@Getter
	@Setter
	@Column(name = "http_status")
	private Integer http_status;

	// RELACIONAMENTO: (RawBody -> PayloadField)
	@Getter
	@Setter
	@OneToOne(mappedBy = "rawBody", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
	private PayloadField payload_fields; // Chave idêntica ao do JSON.

	// RELACIONAMENTO: (RawBody -> Metadata)
	@Getter
	@Setter
	@OneToOne(mappedBy = "rawBody", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
	private Metadata metadata;

	// NÃO fazer toString() no PAI dos RELACIONAMENTOS!
	// (OneToOne, no caso pois Gera Loop)
	@Override
	public String toString() {
		return "RawBody [id=" + id + ", app_id=" + app_id + ", dev_id=" + dev_id + ", hardware_serial="
				+ hardware_serial + ", port=" + port + ", counter=" + counter + ", payload_raw=" + payload_raw
				+ ", downlink_url=" + downlink_url + ", is_retry=" + is_retry + ", confirmed=" + confirmed
				+ ", schedule=" + schedule + ", filter=" + filter + ", ack=" + ack + ", http_status=" + http_status
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ack == null) ? 0 : ack.hashCode());
		result = prime * result + ((app_id == null) ? 0 : app_id.hashCode());
		result = prime * result + ((confirmed == null) ? 0 : confirmed.hashCode());
		result = prime * result + ((counter == null) ? 0 : counter.hashCode());
		result = prime * result + ((dev_id == null) ? 0 : dev_id.hashCode());
		result = prime * result + ((downlink_url == null) ? 0 : downlink_url.hashCode());
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
		result = prime * result + ((hardware_serial == null) ? 0 : hardware_serial.hashCode());
		result = prime * result + ((http_status == null) ? 0 : http_status.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((is_retry == null) ? 0 : is_retry.hashCode());
		result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
		result = prime * result + ((payload_fields == null) ? 0 : payload_fields.hashCode());
		result = prime * result + ((payload_raw == null) ? 0 : payload_raw.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
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
		RawBody other = (RawBody) obj;
		if (ack == null) {
			if (other.ack != null)
				return false;
		} else if (!ack.equals(other.ack))
			return false;
		if (app_id == null) {
			if (other.app_id != null)
				return false;
		} else if (!app_id.equals(other.app_id))
			return false;
		if (confirmed == null) {
			if (other.confirmed != null)
				return false;
		} else if (!confirmed.equals(other.confirmed))
			return false;
		if (counter == null) {
			if (other.counter != null)
				return false;
		} else if (!counter.equals(other.counter))
			return false;
		if (dev_id == null) {
			if (other.dev_id != null)
				return false;
		} else if (!dev_id.equals(other.dev_id))
			return false;
		if (downlink_url == null) {
			if (other.downlink_url != null)
				return false;
		} else if (!downlink_url.equals(other.downlink_url))
			return false;
		if (filter != other.filter)
			return false;
		if (hardware_serial == null) {
			if (other.hardware_serial != null)
				return false;
		} else if (!hardware_serial.equals(other.hardware_serial))
			return false;
		if (http_status == null) {
			if (other.http_status != null)
				return false;
		} else if (!http_status.equals(other.http_status))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (is_retry == null) {
			if (other.is_retry != null)
				return false;
		} else if (!is_retry.equals(other.is_retry))
			return false;
		if (metadata == null) {
			if (other.metadata != null)
				return false;
		} else if (!metadata.equals(other.metadata))
			return false;
		if (payload_fields == null) {
			if (other.payload_fields != null)
				return false;
		} else if (!payload_fields.equals(other.payload_fields))
			return false;
		if (payload_raw == null) {
			if (other.payload_raw != null)
				return false;
		} else if (!payload_raw.equals(other.payload_raw))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		return true;
	}

}