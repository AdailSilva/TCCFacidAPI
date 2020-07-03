package com.adailsilva.tcc.facid.api.mqtt;

import java.net.URISyntaxException;
import java.util.List;

import org.thethingsnetwork.data.common.Connection;
import org.thethingsnetwork.data.common.Metadata;
import org.thethingsnetwork.data.common.Metadata.Gateway;
import org.thethingsnetwork.data.common.messages.DataMessage;
import org.thethingsnetwork.data.common.messages.UplinkMessage;
import org.thethingsnetwork.data.mqtt.Client;

public class ClienteTTN {
	private static void capturarDados(DataMessage dado) {

		// Dados:
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------");		
		System.out.println("==================================>>> DADO <<<===================================");		
		System.out.println("ID DO APLICATIVO                : " + ((UplinkMessage) dado).getAppId());
		System.out.println("CONTADOR DE QUADROS DE UPLINK   : " + ((UplinkMessage) dado).getCounter());
		System.out.println("ID DO DISPOSITIVO               : " + ((UplinkMessage) dado).getDevId());
		System.out.println("SERIAL DO HARDWARE              : " + ((UplinkMessage) dado).getHardwareSerial());
		System.out.println("PORTA DE CRIPTOGRAFIA           : " + ((UplinkMessage) dado).getPort());

		boolean resposta = ((UplinkMessage) dado).isRetry();
		System.out.println("É uma NOVA tentativa de uplink? : " + (resposta ? "Sim." : "Não."));
		System.out.println();

		// Metadados do pacote de UPLINK:
		Metadata metadado = ((UplinkMessage) dado).getMetadata();
		System.out.println("================================>>> METADADOS <<<================================");
		System.out.println("TAXA DE BITS                    : " + metadado.getBitRate());
		System.out.println("TAXA DE CODIFICAÇÃO             : " + metadado.getCodingRate());
		System.out.println("TAXA DE DADOS                   : " + metadado.getDataRate());
		System.out.println("FREQUÊNCIA                      : " + metadado.getFrequency());
		System.out.println("MODULAÇÃO                       : " + metadado.getModulation());
		System.out.println("TEMPO                           : " + metadado.getTime());
		System.out.println();

		// Gateways:
		List<Gateway> gateways = ((UplinkMessage) dado).getMetadata().getGateways();
		System.out.println("================================>>> GATEWAYS <<<=================================");

		// Percorrer o List:
		for (int i = 0; i < gateways.size(); i++) {
			System.out.println("ALTITUDE                        : " + gateways.get(i).getAltitude());
			System.out.println("CANAL                           : " + gateways.get(i).getChannel());
			System.out.println("ID GATEWAY                      : " + gateways.get(i).getId());
			System.out.println("LATITUDE                        : " + gateways.get(i).getLatitude());
			System.out.println("LONGITUDE                       : " + gateways.get(i).getLongitude());
			System.out.println("RF_CHAIN                        : " + gateways.get(i).getRfChain());
			System.out.println("RSSI                            : " + gateways.get(i).getRssi());
			System.out.println("SNR                             : " + gateways.get(i).getSnr());
			System.out.println("TIME                            : " + gateways.get(i).getTime());
			System.out.println("TIMESTAMP                       : " + gateways.get(i).getTimestamp());
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println();
		}

		// Payload:
		System.out.println("=================================>>> PAYLOAD <<<=================================");
		// Obtenha os campos de carga útil:
		System.out.println("Payload : " + ((UplinkMessage) dado).getPayloadFields().get("payload_fields"));

		// Decodificado com Base64.getDecoder():
		byte[] payload_raw = ((UplinkMessage) dado).getPayloadRaw();
		System.out.print("DADO RAW                        : ");

		for (int i = 0; i < payload_raw.length; i++) {
			char caractere = (char) payload_raw[i];
			System.out.print(caractere);
		}

		System.out.println();
		// alertaSolo((String) ((UplinkMessage)dado).getPayloadFields().get("payload"));
		// System.out.println();
		// System.out.println();
	}

	/*
	 * public static void alertaSolo(String status) { if (status.equals("SECO")) {
	 * System.out.println("+--------------------------+");
	 * System.out.println("| Status: Solo Seco! | ");
	 * System.out.println("+--------------------------+"); PushBullet.send("seco");
	 * } else if (status.equals("MOLHADO")) {
	 * System.out.println("+-----------------------+");
	 * System.out.println("| Status: Solo Molhado! |");
	 * System.out.println("+-----------------------+"); PushBullet.send("molhado");
	 * } else { System.out.println("+-------------+");
	 * System.out.println("| Status: OK! |"); System.out.println("+-------------+");
	 * } }
	 */
	public static void main(String[] args) {
		String region = "eu";
		String appId = "devices_au915-928";
		String accessKey = "ttn-account-v2.jQmX1K3ysVNtkq-e1JBfarAHxEO4YSirAaidtFV1aP4";
		try {
			Client clienteTTN = new Client(region, appId, accessKey);
			clienteTTN.onError((Throwable _error) -> System.err.println("Erro: " + _error.getMessage()));
			clienteTTN.onConnected((Connection _client) -> System.out
					.println("('~')/ Java Conectado a The Things Network - TTN, via protocolo MQTT."));
			clienteTTN.onMessage((String devId, DataMessage dado) -> {
				// Chamando Método "principal da aplicação".
				capturarDados(dado);
			});
			clienteTTN.start();
		} catch (URISyntaxException e1) {
			System.err.println(e1.getMessage());
			e1.printStackTrace();
		} catch (Exception e2) {
			System.err.println(e2.getMessage());
			e2.printStackTrace();
		}
	}

}
