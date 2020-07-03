package com.adailsilva.tcc.facid.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adailsilva.tcc.facid.api.model.Pessoa;
import com.adailsilva.tcc.facid.api.repository.pessoa.PessoaRepositoryQuery;

// Extendendo Interfaces personalizadas além da JpaRepository.
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

	// Buscar Pessoas com SQL Nativo: ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT * FROM tccfacidapi.pessoas", nativeQuery = true)
	List<Pessoa> listarPessoas();

}
