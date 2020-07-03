package com.adailsilva.tcc.facid.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.adailsilva.tcc.facid.api.model.Metadata;
import com.adailsilva.tcc.facid.api.model.PayloadField;
import com.adailsilva.tcc.facid.api.model.RawBody;
import com.adailsilva.tcc.facid.api.model.TipoPacote;
import com.adailsilva.tcc.facid.api.repository.MetadataRepository;
import com.adailsilva.tcc.facid.api.repository.PayloadFieldRepository;
import com.adailsilva.tcc.facid.api.repository.RawBodyRepository;

// Anotação "@Service" diz que a classe será um componente do Spring.
// Para o Spring poder ejetá-la quando preciso.
@Service
public class RawBodyService {

	@Autowired
	private RawBodyRepository rawBodyRepository;

	@Autowired
	private PayloadFieldRepository payloadFieldRepository;

	@Autowired
	private MetadataRepository metadataRepository;

	// [C]RUD - CREATE
	public RawBody salvar(RawBody rawBody) {
		return rawBodyRepository.save(rawBody);
	}

	// Não há necessidade de trazer para uma classe de serviço.
	// C[R]UD - READ 1 (Direto do Repository Padrão)
	// C[R]UD - READ 2 (Direto do Repository Padrão)
	// C[R]UD - READ 3 (Com Paginação) (Implementado do Repository Customizado)
	// C[R]UD - READ 4 [ PROJEÇÃO ] (Implementado do Repository Customizado)

	// CR[U]D - UPDATE (TOTAL)
	// Também preciso agora receber o id.
	public RawBody atualizar(Long id, RawBody rawBody) {
		
		RawBody rawBodyRecuperado = buscarRawBodyPeloId(id);
		// Classe utilitária do Spring para copiar propriedades de um objeto para outro.
		// source = objeto recebido, target objeto alvo e ignoreProperties para ignorar
		// o que não se quer copiar.
		BeanUtils.copyProperties(rawBody, rawBodyRecuperado, "id");
		RawBody rawBodySalvo = rawBodyRepository.save(rawBodyRecuperado);
		// Preenchendo a coluna de relacionamento: "raw_bodies_id".
		// (PaylodField -> RawBody).
		PayloadField payloadField = rawBodyRecuperado.getPayload_fields();
		payloadField.setRawBody(rawBodyRecuperado); // ID apenas.
		payloadFieldRepository.save(payloadField); // Update.
		// Preenchendo a coluna de relacionamento: "raw_bodies_id".
		// (Metadata -> RawBody).
		Metadata metadata = rawBodyRecuperado.getMetadata();
		metadata.setRawBody(rawBodyRecuperado); // ID apenas.
		metadataRepository.save(metadata); // Update.
		return rawBodyRepository.save(rawBodySalvo);
	}

	// CR[U]D - UPDATE (PARCIAL - NÃO FAZ USO DO VERBO PATCH)
	// Método com regra de negócio para atualização parcial do recurso.
	public void atualizarPropriedadeIsRetry(Long id, Boolean is_retry) {
		
		RawBody rawBodySalvo = buscarRawBodyPeloId(id);
		rawBodySalvo.setIs_retry(is_retry); // aqui poderá ser true ou false, 0 ou 1.
		rawBodyRepository.save(rawBodySalvo);
	}

	// Não há necessidade de trazer para uma classe de serviço.
	// CRU[D] - DELETE (Direto do Repository Padrão)

	// Método criado para evitar duplicação de código.
	private RawBody buscarRawBodyPeloId(Long id) {
		
		RawBody rawBodySalvo = rawBodyRepository.findOne(id);
		// Regra para evitar objeto nulo, caso não encontre o recurso pelo id.
		if (rawBodySalvo == null) {
			// Lançando uma exceção adequada ao caso:
			// throw new EmptyResultDataAccessException(expectedSize);
			// Hum (1), pois esperava pelo menos um elemento e nada foi encontrado (0).
			throw new EmptyResultDataAccessException(1);
		}
		return rawBodySalvo;
	}

	// C[R]UD (Método auxiliar para consultas importantes)
	public List<RawBody> upDownPacotes(RawBody rawBody) {

		// Buscar pacote do último uplink:
		RawBody rawBodyUltimoUplink = rawBodyRepository.buscarUltimoUplink(rawBody.getApp_id(), rawBody.getDev_id());

		// Buscar pacote do último downlink:
		RawBody rawBodyUltimoDownlink = rawBodyRepository.buscarUltimoDownlink(rawBody.getApp_id(),
				rawBody.getDev_id());

		// [UPLINK] ÚLTIMA CONFIRMAÇÃO DE RECEBIMENTO DOWNLINK: [confirmed ack]
		RawBody rawBodyUltimoUplinkACK = rawBodyRepository.buscarUltimaConfirmacaoDownlink(rawBody.getApp_id(),
				rawBody.getDev_id());

		// ÚLTIMO [DOWNLINK] SEM PEDIDO DE CONFIRMAÇÃO: [scheduled]
		RawBody rawBodyUltimoDownlinkSemPedidoConfirmacao = rawBodyRepository
				.buscarUltimoDownlinkSemPedidoConfirmacao(rawBody.getApp_id(), rawBody.getDev_id());

		// ÚLTIMO [DOWNLINK] COM PEDIDO DE CONFIRMAÇÃO: [scheduled confirmed]
		RawBody rawBodyUltimoDownlinkPedidoConfirmacao = rawBodyRepository
				.buscarUltimoDownlinkPedidoConfirmacao(rawBody.getApp_id(), rawBody.getDev_id());

		// ÚLTIMO [DOWNLINK] TENTATIVA DE CONFIRMAÇÃO: [confirmed]
		RawBody rawBodyUltimoDownlinkTentativaConfirmacao = rawBodyRepository
				.buscarUltimoDownlinkTentativaConfirmacao(rawBody.getApp_id(), rawBody.getDev_id());

		// Verificação de ordem dos contadores:
		// Para saber se o último downlink foi ou não confirmado.
		if (rawBodyUltimoUplinkACK.getId() < rawBodyUltimoDownlinkPedidoConfirmacao.getId()
				&& rawBodyUltimoDownlinkSemPedidoConfirmacao.getId() < rawBodyUltimoDownlinkPedidoConfirmacao.getId()) {
			System.out.println("=================================================================");
			System.out.println("-------> Ainda não Houve Confirmação do último Downlink! <-------");
			System.out.println("=================================================================");

		} else {
			System.out.println("=================================================================");
			System.out.println("--> ACK OK! --> Para o último Downlink com pedido de Confirmação.");
			System.out.println("=================================================================");
			System.out.println("");
		}

		System.out.println("");
		System.out.println("============================================");
		System.out.println("=========== ÚLTIMO UPLINK SALVO ============");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoUplink);

		System.out.println("");
		System.out.println("============================================");
		System.out.println("=========== ÚLTIMO DOWNLINK SALVO ==========");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoDownlink);

		System.out.println("");
		System.out.println("============================================");
		System.out.println("======= ÚLTIMO UPLINK SALVO com ACK ========");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoUplinkACK);

		System.out.println("");
		System.out.println("============================================");
		System.out.println("====== ÚLTIMO DOWNLINK sem Pedido ACK ======");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoDownlinkSemPedidoConfirmacao);

		System.out.println("");
		System.out.println("============================================");
		System.out.println("====== ÚLTIMO DOWNLINK com Pedido ACK ======");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoDownlinkPedidoConfirmacao);

		System.out.println("");
		System.out.println("============================================");
		System.out.println("====== ÚLTIMO DOWNLINK Tentativa ACK =======");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoDownlinkTentativaConfirmacao);

		// Preparando retorno:
		ArrayList<RawBody> rawBodies = new ArrayList<RawBody>();
		rawBodies.add(rawBodyUltimoUplink);
		rawBodies.add(rawBodyUltimoDownlink);
		rawBodies.add(rawBodyUltimoUplinkACK);
		rawBodies.add(rawBodyUltimoDownlinkSemPedidoConfirmacao);
		rawBodies.add(rawBodyUltimoDownlinkPedidoConfirmacao);
		rawBodies.add(rawBodyUltimoDownlinkTentativaConfirmacao);

		return rawBodies;
	}

	// ===============================================================================
	// ====================== TTN -> API -> TTN <- API <- TTN ======================
	// ===============================================================================

	// === Uplink ===
	public RawBody uplink(RawBody jsonRawBody) {
		
		// Se é mensagem de ACK TTN [Confirmed ack].
		if (jsonRawBody.getPort() == 0 && jsonRawBody.getPayload_raw() == null && jsonRawBody.getConfirmed() == null) {
			// Setando confirmação de Recebimento do último downlink.
			jsonRawBody.setConfirmed(true);
			jsonRawBody.setAck("confirmed ack");
			System.out.println("");
			System.out.println("====================================================================");
			System.out.println("===== UPLINK de Confirmação do último Downlink [confirmed ack] =====");
			System.out.println("====================================================================");
			System.out.println("");
		} else {
			jsonRawBody.setConfirmed(false);
		}
		// Setando o tipo do pacote:
		jsonRawBody.setFilter(TipoPacote.UPLINK);
		RawBody rawBodySalvo = rawBodyRepository.save(jsonRawBody);
		// Preenchendo a coluna de relacionamento: "raw_bodies_id".
		// (PaylodField -> RawBody).
		PayloadField payloadField = rawBodySalvo.getPayload_fields();
		payloadField.setRawBody(rawBodySalvo); // ID apenas.
		payloadFieldRepository.save(payloadField); // Update.
		// Preenchendo a coluna de relacionamento: "raw_bodies_id".
		// (Metadata -> RawBody).
		Metadata metadata = rawBodySalvo.getMetadata();
		metadata.setRawBody(rawBodySalvo); // ID apenas.
		metadataRepository.save(metadata); // Update.
		// Buscando Resumo atual dos pacotes:
		buscarResumoPacotes(rawBodySalvo);

		return rawBodySalvo;
	}

	// === Downlink ===
	public RawBody downlink(RawBody rawBody) throws IOException {
		
		// Setando o tipo do Pacote:
		rawBody.setFilter(TipoPacote.DOWNLINK);
		// Setando tipo de Confirmação de todos os Downlinks:
		if (rawBody.getConfirmed() == true) {
			rawBody.setAck("scheduled confirmed");
		} else if (rawBody.getConfirmed() == false) {
			rawBody.setAck("scheduled");
		} else {
			rawBody.setAck(null);
		}

		// Buscar pacote do último uplink:
		RawBody rawBodyUltimoUplink = rawBodyRepository.buscarUltimoUplink(rawBody.getApp_id(), rawBody.getDev_id());

		// Salvando os dados do Downlink:
		rawBody.setDownlink_url(rawBodyUltimoUplink.getDownlink_url());
		RawBody rawBodySalvo = rawBodyRepository.save(rawBody); // incluso "downlink_url".
		System.out.println("");
		System.out.println("======================================");
		System.out.println("=========== DOWNLINK SALVO ===========");
		System.out.println("======================================");
		System.out.println(rawBody);
		System.out.println("");
		System.out.println("======================================================");
		System.out.println("HTTP POST na última DownlinkUrl (recuperada do banco):");
		System.out.println("======================================================");
		// Pegando a última Downlink_URL:
		System.out.println(rawBodyUltimoUplink.getDownlink_url());
		System.out.println("");
		// Fazer um POST interno:
		int respostaRequisicao = fazerPost(rawBodySalvo);
		rawBodySalvo.setHttp_status(respostaRequisicao);

		return rawBodyRepository.save(rawBodySalvo); // incluso "http_status".;
	}

	// === Buscar Resumo Final de Pacotes ===
	public List<RawBody> buscarResumoPacotes(RawBody rawBody) {
		
		// Buscar pacote do último uplink:
		RawBody rawBodyUltimoUplink = rawBodyRepository.buscarUltimoUplink(rawBody.getApp_id(), rawBody.getDev_id());
		// Buscar pacote do último downlink:
		RawBody rawBodyUltimoDownlink = rawBodyRepository.buscarUltimoDownlink(rawBody.getApp_id(),
				rawBody.getDev_id());
		// [UPLINK] ÚLTIMA CONFIRMAÇÃO DE RECEBIMENTO DOWNLINK: [confirmed ack]
		RawBody rawBodyUltimoUplinkACK = rawBodyRepository.buscarUltimaConfirmacaoDownlink(rawBody.getApp_id(),
				rawBody.getDev_id());
		// ÚLTIMO [DOWNLINK] SEM PEDIDO DE CONFIRMAÇÃO: [scheduled]
		RawBody rawBodyUltimoDownlinkSemPedidoConfirmacao = rawBodyRepository
				.buscarUltimoDownlinkSemPedidoConfirmacao(rawBody.getApp_id(), rawBody.getDev_id());
		// ÚLTIMO [DOWNLINK] COM PEDIDO DE CONFIRMAÇÃO: [scheduled confirmed]
		RawBody rawBodyUltimoDownlinkPedidoConfirmacao = rawBodyRepository
				.buscarUltimoDownlinkPedidoConfirmacao(rawBody.getApp_id(), rawBody.getDev_id());
		// ÚLTIMO [DOWNLINK] TENTATIVA DE CONFIRMAÇÃO: [confirmed]
		RawBody rawBodyUltimoDownlinkTentativaConfirmacao = rawBodyRepository
				.buscarUltimoDownlinkTentativaConfirmacao(rawBody.getApp_id(), rawBody.getDev_id());
		// Verificação de ordem dos contadores:
		// Para saber se o último downlink foi ou não confirmado.
		if (rawBodyUltimoUplinkACK.getId() < rawBodyUltimoDownlinkPedidoConfirmacao.getId()
				&& rawBodyUltimoDownlinkSemPedidoConfirmacao.getId() < rawBodyUltimoDownlinkPedidoConfirmacao.getId()) {
			System.out.println("=================================================================");
			System.out.println("-------> Ainda não Houve Confirmação do último Downlink! <-------");
			System.out.println("=================================================================");
			// Persistir um DOWNLINK com filter: [confirmed], caso o último downlink
			// com pedido de confirmação não tenha recebido o seu [confirmed ack].
			RawBody rbTemp = new RawBody();
			rbTemp.setFilter(TipoPacote.DOWNLINK);
			rbTemp.setAck("confirmed");
			rawBodyRepository.acrescentarDownlinkTipoConfirmed(rawBody.getApp_id(), rawBody.getDev_id(),
					rawBody.getPort(), rawBody.getConfirmed(), rbTemp.getFilter().toString(), rbTemp.getAck());
		} else {
			System.out.println("=================================================================");
			System.out.println("--> ACK OK! --> Para o último Downlink com pedido de Confirmação.");
			System.out.println("=================================================================");
			System.out.println("");
		}
		System.out.println("");
		System.out.println("============================================");
		System.out.println("=========== ÚLTIMO UPLINK SALVO ============");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoUplink);
		System.out.println("");
		System.out.println("============================================");
		System.out.println("=========== ÚLTIMO DOWNLINK SALVO ==========");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoDownlink);
		System.out.println("");
		System.out.println("============================================");
		System.out.println("======= ÚLTIMO UPLINK SALVO com ACK ========");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoUplinkACK);
		System.out.println("");
		System.out.println("============================================");
		System.out.println("====== ÚLTIMO DOWNLINK sem Pedido ACK ======");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoDownlinkSemPedidoConfirmacao);
		System.out.println("");
		System.out.println("============================================");
		System.out.println("====== ÚLTIMO DOWNLINK com Pedido ACK ======");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoDownlinkPedidoConfirmacao);
		System.out.println("");
		System.out.println("============================================");
		System.out.println("====== ÚLTIMO DOWNLINK Tentativa ACK =======");
		System.out.println("============================================");
		System.out.println(rawBodyUltimoDownlinkTentativaConfirmacao);
		// Preparando retorno:
		ArrayList<RawBody> rawBodies = new ArrayList<RawBody>();
		rawBodies.add(rawBodyUltimoUplink);
		rawBodies.add(rawBodyUltimoDownlink);
		rawBodies.add(rawBodyUltimoUplinkACK);
		rawBodies.add(rawBodyUltimoDownlinkSemPedidoConfirmacao);
		rawBodies.add(rawBodyUltimoDownlinkPedidoConfirmacao);
		rawBodies.add(rawBodyUltimoDownlinkTentativaConfirmacao);

		return rawBodies;
	}

	public int fazerPost(RawBody rawBodySalvoEmDownlink) throws IOException {
		
		// POST na útima Downlink_URL registrada no último Uplink:
		// Altere a URL com qualquer outro recurso POST acessível ao público,
		// que aceita o corpo da solicitação JSON.
		URL url = new URL(rawBodySalvoEmDownlink.getDownlink_url());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		// Site para conversão de JSON para String:
		// https://tools.knowledgewalls.com/jsontostring
		// String jsonInputString =
		// "{\"dev_id\":\"mote_09_d1mini_otaa\",\"port\":0,\"confirmed\":true,\"payload_raw\":\"AQIDBA==\",\"schedule\":\"replace\"}";
		String jsonInputString = "{\"dev_id\":\"" + rawBodySalvoEmDownlink.getDev_id() + "\",\"port\":"
				+ rawBodySalvoEmDownlink.getPort() + ",\"confirmed\":" + rawBodySalvoEmDownlink.getConfirmed()
				+ ",\"payload_raw\":\"" + rawBodySalvoEmDownlink.getPayload_raw() + "\",\"schedule\":\""
				+ rawBodySalvoEmDownlink.getSchedule() + "\"}";
		System.out.println("");
		System.out.println("JSON Formado a partir dos dados do Corpo da Requisição:");
		System.out.println(jsonInputString);
		System.out.println("");
		// Subindo JSON:
		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		int code = con.getResponseCode();
		System.out.println("==================================");
		System.out.println("========== REPOSTA HTTP ==========");
		System.out.println("==================================");
		System.out.print("Código: [ " + code + " ] ");
		if (code == 202) {
			System.out.println(" --> \"O código de resposta HyperText Transfer Protocol (HTTP) 202 Accepted "
					+ "indica que a requisição foi recebida, mas não pode atuar ainda.\"");
		}
		// Montando a resposta:
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder resposta = new StringBuilder();
			String linhaResposta = null;
			while ((linhaResposta = br.readLine()) != null) {
				resposta.append(linhaResposta.trim());
			}
			System.out.println(resposta.toString());
		}
		// Encerrando a conexão:
		con.disconnect();

		return code;
	}
}
