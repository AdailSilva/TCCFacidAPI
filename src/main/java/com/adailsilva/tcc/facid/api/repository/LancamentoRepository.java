package com.adailsilva.tcc.facid.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adailsilva.tcc.facid.api.model.Lancamento;
import com.adailsilva.tcc.facid.api.repository.lancamento.LancamentoRepositoryQuery;

// Extendendo Interfaces personalizadas além da JpaRepository.
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

	// Buscar Lancamentos com SQL Nativo: ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT * FROM tccfacidapi.lancamentos", nativeQuery = true)
	List<Lancamento> listarLancamentos();

}
