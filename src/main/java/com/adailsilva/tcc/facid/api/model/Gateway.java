package com.adailsilva.tcc.facid.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "Gateway")
@Table(name = "gateways")
public class Gateway implements Serializable {

	private static final long serialVersionUID = 8004137663235986373L;

	@Id
	// A tabela que toma conta do preenchimento dos ids:
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Size(min = 1, max = 50)
	@Getter
	@Setter
	@Column(name = "id")
	private Long id;

	// @NotNull
	@Size(min = 1, max = 20)
	@Getter
	@Setter
	@Column(name = "gtw_id")
	private String gtw_id;

	// @NotNull
	@Size(min = 1, max = 10)
	@Getter
	@Setter
	@Column(name = "timestamp")
	private String timestamp;

	// @NotNull
	@Size(min = 0, max = 30)
	@Getter
	@Setter
	@Column(name = "time")
	private String time;

	// @NotNull
	// @Size(min = 1, max = 1)
	@Getter
	@Setter
	@Column(name = "channel")
	private Integer channel;

	// @NotNull
	// @Size(min = 1, max = 3)
	@Getter
	@Setter
	@Column(name = "rssi")
	private Integer rssi;

	// @NotNull
	// @Size(min = 1, max = 1)
	@Getter
	@Setter
	@Column(name = "snr")
	private Float snr;

	// @NotNull
	// @Size(min = 1, max = 1)
	@Getter
	@Setter
	@Column(name = "rf_chain")
	private Integer rf_chain;

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

	// RELACIONAMENTO: (Gateway -> Metadata)
	// Não necessária implementação deste lado.

	@Override
	public String toString() {
		return "Gateway [id=" + id + ", gtw_id=" + gtw_id + ", timestamp=" + timestamp + ", time=" + time + ", channel="
				+ channel + ", rssi=" + rssi + ", snr=" + snr + ", rf_chain=" + rf_chain + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", altitude=" + altitude + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((altitude == null) ? 0 : altitude.hashCode());
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((gtw_id == null) ? 0 : gtw_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((rf_chain == null) ? 0 : rf_chain.hashCode());
		result = prime * result + ((rssi == null) ? 0 : rssi.hashCode());
		result = prime * result + ((snr == null) ? 0 : snr.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
		Gateway other = (Gateway) obj;
		if (altitude == null) {
			if (other.altitude != null)
				return false;
		} else if (!altitude.equals(other.altitude))
			return false;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (gtw_id == null) {
			if (other.gtw_id != null)
				return false;
		} else if (!gtw_id.equals(other.gtw_id))
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
		if (rf_chain == null) {
			if (other.rf_chain != null)
				return false;
		} else if (!rf_chain.equals(other.rf_chain))
			return false;
		if (rssi == null) {
			if (other.rssi != null)
				return false;
		} else if (!rssi.equals(other.rssi))
			return false;
		if (snr == null) {
			if (other.snr != null)
				return false;
		} else if (!snr.equals(other.snr))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

}