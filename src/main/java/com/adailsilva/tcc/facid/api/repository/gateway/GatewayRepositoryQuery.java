package com.adailsilva.tcc.facid.api.repository.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adailsilva.tcc.facid.api.model.Gateway;
import com.adailsilva.tcc.facid.api.repository.filter.GatewayFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoGateway;

// Obrigatório iniciar com o mesmo nome da classe de repositório.
public interface GatewayRepositoryQuery {

	// Refatoração após implementação da paginação:
	public Page<Gateway> filtrar(GatewayFilter gatewayFilter, Pageable pageable);

	// Implementação da PROJEÇÃO de gateways:
	public Page<ResumoGateway> resumir(GatewayFilter gatewayFilter, Pageable pageable);

}
