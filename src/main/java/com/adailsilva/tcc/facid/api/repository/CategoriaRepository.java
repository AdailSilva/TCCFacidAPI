package com.adailsilva.tcc.facid.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adailsilva.tcc.facid.api.model.Categoria;
import com.adailsilva.tcc.facid.api.repository.categoria.CategoriaRepositoryQuery;

// Extendendo Interfaces personalizadas além da JpaRepository.
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, CategoriaRepositoryQuery {

	// Buscar Categorias com SQL Nativo: ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT * FROM tccfacidapi.categorias", nativeQuery = true)
	List<Categoria> listarCategorias();

}
