package com.adailsilva.tcc.facid.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adailsilva.tcc.facid.api.model.PayloadField;
import com.adailsilva.tcc.facid.api.repository.payloadfield.PayloadFieldRepositoryQuery;

// Extendendo Interfaces personalizadas além da JpaRepository.
public interface PayloadFieldRepository extends JpaRepository<PayloadField, Long>, PayloadFieldRepositoryQuery {

	// Buscar PayloadFields com SQL Nativo: ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT * FROM tccfacidapi.payload_fields", nativeQuery = true)
	List<PayloadField> listarPayloadFields();

}
