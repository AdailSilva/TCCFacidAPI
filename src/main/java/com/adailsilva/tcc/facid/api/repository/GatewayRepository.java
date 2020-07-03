package com.adailsilva.tcc.facid.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adailsilva.tcc.facid.api.model.Gateway;
import com.adailsilva.tcc.facid.api.repository.gateway.GatewayRepositoryQuery;

// Extendendo Interfaces personalizadas além da JpaRepository.
public interface GatewayRepository extends JpaRepository<Gateway, Long>, GatewayRepositoryQuery {

	// Buscar Gateways com SQL Nativo: ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT * FROM tccfacidapi.gateways", nativeQuery = true)
	List<Gateway> listarGateways();

}
