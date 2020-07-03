package com.adailsilva.tcc.facid.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adailsilva.tcc.facid.api.model.Metadata;
import com.adailsilva.tcc.facid.api.repository.metadata.MetadataRepositoryQuery;

// Extendendo Interfaces personalizadas além da JpaRepository.
public interface MetadataRepository extends JpaRepository<Metadata, Long>, MetadataRepositoryQuery {

	// Buscar Metadata com SQL Nativo: ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT * FROM tccfacidapi.metadata", nativeQuery = true)
	List<Metadata> listarMetadata();

}
