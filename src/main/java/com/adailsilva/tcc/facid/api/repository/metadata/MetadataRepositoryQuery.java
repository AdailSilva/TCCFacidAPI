package com.adailsilva.tcc.facid.api.repository.metadata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adailsilva.tcc.facid.api.model.Metadata;
import com.adailsilva.tcc.facid.api.repository.filter.MetadataFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoMetadata;

// Obrigatório iniciar com o mesmo nome da classe de repositório.
public interface MetadataRepositoryQuery {

	// Refatoração após implementação da paginação:
	public Page<Metadata> filtrar(MetadataFilter metadataFilter, Pageable pageable);

	// Implementação da PROJEÇÃO de metadata:
	public Page<ResumoMetadata> resumir(MetadataFilter metadataFilter, Pageable pageable);

}
