package com.adailsilva.tcc.facid.api.repository.rawbody;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adailsilva.tcc.facid.api.model.RawBody;
import com.adailsilva.tcc.facid.api.repository.filter.RawBodyFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoRawBody;

// Obrigatório iniciar com o mesmo nome da classe de repositório.
public interface RawBodyRepositoryQuery {

	// Refatoração após implementação da paginação:
	public Page<RawBody> filtrar(RawBodyFilter rawBodyFilter, Pageable pageable);

	// Implementação da PROJEÇÃO de rawbodies:
	public Page<ResumoRawBody> resumir(RawBodyFilter rawBodyFilter, Pageable pageable);

}
