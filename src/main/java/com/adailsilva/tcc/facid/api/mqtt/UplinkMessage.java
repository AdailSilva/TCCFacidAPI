package com.adailsilva.tcc.facid.api.mqtt;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import org.thethingsnetwork.data.common.Metadata;
import org.thethingsnetwork.data.common.messages.DataMessage;

import lombok.Getter;

/**
 * Esta é uma classe de wrapper para JSONObject para fornecer suporte à carga
 * útil codificada em base64.
 */

public class UplinkMessage implements DataMessage {

	// ID do aplicativo:
	@Getter
	// @Setter
	private String app_id;

	@Getter
	// @Setter
	// ID de dispositivo:
	private String dev_id;

	@Getter
	// @Setter
	// Serial de hardware:
	private String hardware_serial;

	private boolean is_retry;

	@Getter
	// @Setter
	// Porta de criptografia:
	private int port;

	@Getter
	// @Setter
	// Contador de quadro de ligação ascendente:
	private int counter;

	// @Getter
	// @Setter
	// Carga útil bruta:
	private String payload_raw;

	// @Getter
	// @Setter
	private Map<String, Object> payload_fields;

	@Getter
	// @Setter
	// Metadados deste pacote de uplink:
	private Metadata metadata;

	// Carga útil bruta como uma matriz de bytes:
	public byte[] getPayloadRaw() {
		return Base64.getDecoder().decode(payload_raw);
	}

	/*
	 * Obtenha os campos de carga útil. Somente se você tiver uma função de
	 * decodificador. Retorna os campos de carga útil como um mapa onde as chaves
	 * são cadeias e os valores são qualquer entidade válida para json.
	 */
	public Map<String, Object> getPayloadFields() {
		return Collections.unmodifiableMap(payload_fields);
	}

	// Verifique se esta mensagem é uma nova tentativa:
	public boolean isRetry() {
		return is_retry;
	}

}
