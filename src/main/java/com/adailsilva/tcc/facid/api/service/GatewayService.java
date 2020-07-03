package com.adailsilva.tcc.facid.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.adailsilva.tcc.facid.api.model.Gateway;
import com.adailsilva.tcc.facid.api.repository.GatewayRepository;

// Anotação "@Service" diz que a classe será um componente do Spring.
// Para o Spring poder ejetá-la quando preciso.
@Service
public class GatewayService {

	@Autowired
	private GatewayRepository gatewayRepository;

	// [C]RUD - CREATE
	public Gateway salvar(Gateway gateway) {
		
		return gatewayRepository.save(gateway);
	}

	// Não há necessidade de trazer para uma classe de serviço.
	// C[R]UD - READ 1 (Direto do Repository Padrão)
	// C[R]UD - READ 2 (Direto do Repository Padrão)
	// C[R]UD - READ 3 (Com Paginação) (Implementado do Repository Customizado)
	// C[R]UD - READ 4 [ PROJEÇÃO ] (Implementado do Repository Customizado)

	// CR[U]D - UPDATE (TOTAL)
	public Gateway atualizar(Long id, Gateway gateway) {
		
		Gateway gatewaySalvo = buscarGatewayPeloId(id);
		// Classe utilitária do Spring para copiar propriedades de um objeto para outro.
		// source = objeto recebido, target objeto alvo e ignoreProperties para ignorar
		// o que não se quer copiar.
		BeanUtils.copyProperties(gateway, gatewaySalvo, "id");
		return gatewayRepository.save(gatewaySalvo);
	}

	// CR[U]D - UPDATE (PARCIAL - NÃO FAZ USO DO VERBO PATCH)
	public void atualizarPropriedadeTime(Long id, String time) {
		
		Gateway gatewaySalvo = buscarGatewayPeloId(id);
		gatewaySalvo.setTime(time);
		gatewayRepository.save(gatewaySalvo);
	}

	// Não há necessidade de trazer para uma classe de serviço.
	// CRU[D] - DELETE (Direto do Repository Padrão)

	// Método criado para evitar duplicação de código.
	private Gateway buscarGatewayPeloId(Long id) {
		
		Gateway gatewaySalvo = gatewayRepository.findOne(id);
		// Regra para evitar objeto nulo, caso não encontre o recurso pelo id.
		if (gatewaySalvo == null) {
			// Lançando uma exceção adequada ao caso:
			// throw new EmptyResultDataAccessException(expectedSize);
			// Hum (1), pois esperava pelo menos um elemento e nada foi encontrado (0).
			throw new EmptyResultDataAccessException(1);
		}
		return gatewaySalvo;
	}
}
