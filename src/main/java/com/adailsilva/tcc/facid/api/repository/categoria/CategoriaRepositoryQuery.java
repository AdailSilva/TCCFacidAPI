package com.adailsilva.tcc.facid.api.repository.categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adailsilva.tcc.facid.api.model.Categoria;
import com.adailsilva.tcc.facid.api.repository.filter.CategoriaFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoCategoria;

// Obrigatório iniciar com o mesmo nome da classe de repositório.
public interface CategoriaRepositoryQuery {
	
	// Refatoração após implementação da paginação:
	public Page<Categoria> filtrar(CategoriaFilter categoriaFilter, Pageable pageable);

	// Implementação da PROJEÇÃO de categorias:
	public Page<ResumoCategoria> resumir(CategoriaFilter categoriaFilter, Pageable pageable);

}
