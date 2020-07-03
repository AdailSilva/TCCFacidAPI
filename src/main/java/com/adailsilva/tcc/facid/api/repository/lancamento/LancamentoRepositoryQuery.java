package com.adailsilva.tcc.facid.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adailsilva.tcc.facid.api.model.Lancamento;
import com.adailsilva.tcc.facid.api.repository.filter.LancamentoFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoLancamento;

// Obrigatório iniciar com o mesmo nome da classe de repositório.
public interface LancamentoRepositoryQuery {
	
	// Refatoração após implementação da paginação:
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

	// Implementação da PROJEÇÃO de lançamentos:
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}
