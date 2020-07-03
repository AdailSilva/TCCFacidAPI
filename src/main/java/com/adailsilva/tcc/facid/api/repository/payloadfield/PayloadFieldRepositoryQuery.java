package com.adailsilva.tcc.facid.api.repository.payloadfield;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adailsilva.tcc.facid.api.model.PayloadField;
import com.adailsilva.tcc.facid.api.repository.filter.PayloadFieldFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoPayloadField;

// Obrigatório iniciar com o mesmo nome da classe de repositório.
public interface PayloadFieldRepositoryQuery {

	// Refatoração após implementação da paginação:
	public Page<PayloadField> filtrar(PayloadFieldFilter payloadFieldFilter, Pageable pageable);

	// Implementação da PROJEÇÃO de payloadfields:
	public Page<ResumoPayloadField> resumir(PayloadFieldFilter payloadFieldFilter, Pageable pageable);

}
