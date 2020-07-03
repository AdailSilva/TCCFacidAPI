package com.adailsilva.tcc.facid.api.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.adailsilva.tcc.facid.api.model.RawBody;
import com.adailsilva.tcc.facid.api.repository.rawbody.RawBodyRepositoryQuery;

// Extendendo Interfaces personalizadas além da JpaRepository.
public interface RawBodyRepository extends JpaRepository<RawBody, Long>, RawBodyRepositoryQuery {

	// Buscar RawBodies com SQL Nativo: ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT * FROM tccfacidapi.raw_bodies", nativeQuery = true)
	List<RawBody> listarRawBodies();

	// Buscar UM RawBody com SQL Nativo:
	// ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT * FROM tccfacidapi.raw_bodies WHERE id = ?1", nativeQuery = true)
	RawBody buscarRawBodyPorID(Long id);

	// ÚLTIMO [UPLINK]:
	// ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT rb. * FROM tccfacidapi.raw_bodies rb WHERE rb.app_id = ?1 AND rb.dev_id = ?2 AND rb.id = (SELECT MAX(id) FROM tccfacidapi.raw_bodies WHERE filter = 'UPLINK')", nativeQuery = true)
	RawBody buscarUltimoUplink(String app_id, String dev_id);

	// ÚLTIMO [DOWNLINK]:
	// ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT rb. * FROM tccfacidapi.raw_bodies rb WHERE rb.app_id = ?1 AND rb.dev_id = ?2 AND rb.id = (SELECT MAX(id) FROM tccfacidapi.raw_bodies WHERE filter = 'DOWNLINK')", nativeQuery = true)
	RawBody buscarUltimoDownlink(String app_id, String dev_id);

	// [UPLINK] ÚLTIMA CONFIRMAÇÃO DE RECEBIMENTO DOWNLINK: [confirmed ack]
	// ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT rb. * FROM tccfacidapi.raw_bodies rb WHERE rb.app_id = ?1 AND rb.dev_id = ?2 AND rb.id = (SELECT MAX(id) FROM tccfacidapi.raw_bodies WHERE filter = 'UPLINK' AND ack = 'confirmed ack')", nativeQuery = true)
	RawBody buscarUltimaConfirmacaoDownlink(String app_id, String dev_id);

	// ÚLTIMO [DOWNLINK] SEM PEDIDO DE CONFIRMAÇÃO: [scheduled]
	// ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT rb. * FROM tccfacidapi.raw_bodies rb WHERE rb.app_id = ?1 AND rb.dev_id = ?2 AND rb.id = (SELECT MAX(id) FROM tccfacidapi.raw_bodies WHERE filter = 'DOWNLINK' AND ack = 'scheduled')", nativeQuery = true)
	RawBody buscarUltimoDownlinkSemPedidoConfirmacao(String app_id, String dev_id);

	// ÚLTIMO [DOWNLINK] COM PEDIDO DE CONFIRMAÇÃO: [scheduled confirmed]
	// ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT rb. * FROM tccfacidapi.raw_bodies rb WHERE rb.app_id = ?1 AND rb.dev_id = ?2 AND rb.id = (SELECT MAX(id) FROM tccfacidapi.raw_bodies WHERE filter = 'DOWNLINK' AND ack = 'scheduled confirmed')", nativeQuery = true)
	RawBody buscarUltimoDownlinkPedidoConfirmacao(String app_id, String dev_id);

	// ÚLTIMO [DOWNLINK] TENTATIVA DE CONFIRMAÇÃO: [confirmed]
	// ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT rb. * FROM tccfacidapi.raw_bodies rb WHERE rb.app_id = ?1 AND rb.dev_id = ?2 AND rb.id = (SELECT MAX(id) FROM tccfacidapi.raw_bodies WHERE filter = 'DOWNLINK' AND ack = 'confirmed')", nativeQuery = true)
	RawBody buscarUltimoDownlinkTentativaConfirmacao(String app_id, String dev_id);

	// INSERIR UM [DOWNLINK] com filter ack: [confirmed]
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO tccfacidapi.raw_bodies (app_id,dev_id,hardware_serial,port,counter,is_retry,confirmed,payload_raw,schedule,filter,ack,downlink_url) VALUES (?1,?2,null,?3,null,null,?4,null,null,?5,?6,null)", nativeQuery = true)
	void acrescentarDownlinkTipoConfirmed(String app_id, String dev_id, Integer port, Boolean confirmed, String filter,
			String ack);

	/*
	 * **************************** RESUMO ****************************
	 * 
	 * último UPLINK	[normal]	[confirmed ack]
	 * 
	 * último DOWNLINK	[schedule]	[schedule confirmed]	[confirmed]
	 * 
	 * UPLINK 	[confirmed ack] últimaConfirmaçãoDownlink
	 * 
	 * DOWNLINK	[scheduled] últimoDownlinkSemPedidoConfirmacao
	 * 
	 * DOWNLINK	[scheduled confirmed] últimoDownlinkPedidoConfirmacao
	 * 
	 * DOWNLINK	[confirmed] últimoDownlinkTentativaConfirmacao
	 */
}
