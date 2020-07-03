package com.adailsilva.tcc.facid.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.adailsilva.tcc.facid.api.model.Lancamento;
import com.adailsilva.tcc.facid.api.model.Pessoa;
import com.adailsilva.tcc.facid.api.repository.LancamentoRepository;
import com.adailsilva.tcc.facid.api.repository.PessoaRepository;
import com.adailsilva.tcc.facid.api.service.exception.PessoaInexistenteOuInativaException;

// Anotação "@Service" diz que a classe será um componente do Spring.
// Para o Spring poder ejetá-la quando preciso.
@Service
public class LancamentoService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	// [C]RUD - CREATE
	public Lancamento salvar(Lancamento lancamento) {

		// Assim retornaria Status 404 para o lançamento, fazendo o cliente pensar que:
		// o Lançamento não existe, porém é o Cliente quem deve ser referenciado.
		// Procurando a pessoa pelo Repositório posso criar uma exceção específica para
		// ela.
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
		// Regra de negócio Sobre pessoa Nula, Ativa ou Inativa:
		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}

	// Não há necessidade de trazer para uma classe de serviço.
	// C[R]UD - READ 1 (Direto do Repository Padrão)
	// C[R]UD - READ 2 (Direto do Repository Padrão)
	// C[R]UD - READ 3 (Com Paginação) (Implementado do Repository Customizado)
	// C[R]UD - READ 4 [ PROJEÇÃO ]  (Implementado do Repository Customizado)

	// CR[U]D - UPDATE (TOTAL)
	public Lancamento atualizar(Long id, Lancamento lancamento) {
		
		Lancamento lancamentoSalvo = buscarLancamentoPeloId(id);
		// Classe utilitária do Spring para copiar propriedades de um objeto para outro.
		// source = objeto recebido, target objeto alvo e ignoreProperties para ignorar
		// o que não se quer copiar.
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");
		return lancamentoRepository.save(lancamentoSalvo);
	}

	// CR[U]D - UPDATE (PARCIAL - NÃO FAZ USO DO VERBO PATCH)
	public void atualizarPropriedadeDescricao(Long id, String descricao) {
		
		Lancamento lancamentoSalvo = buscarLancamentoPeloId(id);
		lancamentoSalvo.setDescricao(descricao);
		lancamentoRepository.save(lancamentoSalvo);
	}

	// Não há necessidade de trazer para uma classe de serviço.
	// CRU[D] - DELETE (Direto do Repository Padrão)

	// Método criado para evitar duplicação de código.
	private Lancamento buscarLancamentoPeloId(Long id) {
		
		Lancamento lancamentoSalvo = lancamentoRepository.findOne(id);
		// Regra para evitar objeto nulo, caso não encontre o recurso pelo id.
		if (lancamentoSalvo == null) {
			// Lançando uma exceção adequada ao caso:
			// throw new EmptyResultDataAccessException(expectedSize);
			// Hum (1), pois esperava pelo menos um elemento e nada foi encontrado (0).
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoSalvo;
	}
}
