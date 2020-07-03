package com.adailsilva.tcc.facid.api.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adailsilva.tcc.facid.api.model.Pessoa;
import com.adailsilva.tcc.facid.api.repository.filter.PessoaFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoPessoa;

// Obrigatório iniciar com o mesmo nome da classe de repositório.
public interface PessoaRepositoryQuery {

	// Refatoração após implementação da paginação:
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);

	// Implementação da PROJEÇÃO de pessoas:
	public Page<ResumoPessoa> resumir(PessoaFilter pessoaFilter, Pageable pageable);

}
