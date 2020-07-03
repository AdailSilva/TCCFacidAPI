package com.adailsilva.tcc.facid.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adailsilva.tcc.facid.api.model.CustomHeader;

public interface CustomHeaderRepository extends JpaRepository<CustomHeader, Long> {

	// Buscar Custom Headers com SQL Nativo: ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT * FROM tccfacidapi.custom_headers", nativeQuery = true)
	List<CustomHeader> listarCustomHeaders();
	
	// Buscar UM Custom Header com SQL Nativo: ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT * FROM tccfacidapi.custom_headers WHERE login = ?1", nativeQuery = true)
	CustomHeader customHeaderPorLogin(String login);
	
}
