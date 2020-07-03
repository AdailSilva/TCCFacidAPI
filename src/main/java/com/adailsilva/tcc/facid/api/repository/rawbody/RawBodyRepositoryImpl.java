package com.adailsilva.tcc.facid.api.repository.rawbody;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.adailsilva.tcc.facid.api.model.RawBody;
import com.adailsilva.tcc.facid.api.model.RawBody_;
import com.adailsilva.tcc.facid.api.repository.filter.RawBodyFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoRawBody;

// Obrigatório iniciar com o mesmo nome da classe de repositório.
public class RawBodyRepositoryImpl implements RawBodyRepositoryQuery {

	// Injetando o EntityManager para a Criteria do JPA.
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<RawBody> filtrar(RawBodyFilter rawBodyFilter, Pageable pageable) {
		
		// CriteriaBuilder é quem constroe as criterias.
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		// Retorno para: "RawBody".
		CriteriaQuery<RawBody> criteria = builder.createQuery(RawBody.class);
		// FILTROS (Restrições como o WHERE de SQL normal):
		// root é usado para pegar os atributos de Lançamento que é feito o filtro.
		// Buscar da raiz que é a entidade "RawBody".
		Root<RawBody> root = criteria.from(RawBody.class);
		// Criar método: "criarRestricoes()".
		Predicate[] predicates = criarRestricoes(rawBodyFilter, builder, root);
		criteria.where(predicates);
		// Criando a Query:
		TypedQuery<RawBody> query = manager.createQuery(criteria);
		// Adicionar na Query a quantidade total de resultados e onde é o começo.
		// Máximo por página e localização da página.
		adicionarRestricoesDePaginacao(query, pageable);

		// Conteúdo, objeto pageable e o total de elementos.
		return new PageImpl<>(query.getResultList(), pageable, total(rawBodyFilter));
	}

	// Lista de predicates para ser retornada;
	// Será criada baseado no que foi informado pelo filtro. que é opcional.
	// Não é necessário passar todos os parâmetros.
	private Predicate[] criarRestricoes(RawBodyFilter rawBodyFilter, CriteriaBuilder builder, Root<RawBody> root) {
		
		// Array de Tamanho variável "Predicate[]" será alimentado
		// Conforme o que foi informado (não nulo).
		List<Predicate> predicates = new ArrayList<>(); // ele será retornado após preenchido.
		// Se for diferente de nulo ele será acrescentado aos critérios.
		// Será considerado apenas os parâmetros informados.
		if (!StringUtils.isEmpty(rawBodyFilter.getApp_id())) {
			// Equivale no SQL "WHERE" descricao "LIKE"... (apenas um Exemplo).
			// se tiver o parâmetro aqui referenciado por exemplo vou add um predicate:
			predicates.add(builder.like(builder.lower(root.get(RawBody_.app_id)),
					"%" + rawBodyFilter.getApp_id().toLowerCase() + "%"));
		}

		// SE EXISTISSEM MAIS PARÂMETROS PARA PESQUISA, FICARIAM AQUI.

		// Transformando predicates "List" em um "Array":
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	// REFATORAÇÃO após implementação de PROJEÇÕES:
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		
		// Total de registros e quando começa.
		int paginaAtual = pageable.getPageNumber(); // size=1&page=0 (page=0).
		int totalRegistrosPorPagina = pageable.getPageSize(); // size=1&page=0 (size=1).
		// Exemplo: se estou na página 2 e quero mostrar 3 registos por página,
		// O primeiro registro será o 6º (sexto). NA VERDADE O SÉTIMO.
		// Páginas começam com ZERO ( 0, 1, 2, 3... ).
		// EU DENOMINO COMO ÚLTIMO REGISTRO DA PÁGINA ANTERIOR. ou seja começará:
		// paginaAtual * totalRegistrosPorPagina + 1.
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
		// DEBUG
		// System.out.println(pageable);
		// System.out.println(paginaAtual);
		// System.out.println(totalRegistrosPorPagina);
		// System.out.println(primeiroRegistroDaPagina);
	}

	private Long total(RawBodyFilter rawBodyFilter) {
		
		// Calcular a quantidade de elementos totais para o filtro.
		// nova consulta com a CRITERIA do JPA:
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		// Retorna o tipo Long já foi tipo RawBody.
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		// Retorno "RawBody":
		Root<RawBody> root = criteria.from(RawBody.class);
		// Acrescentando o filtro.
		Predicate[] predicates = criarRestricoes(rawBodyFilter, builder, root);
		criteria.where(predicates);
		// No SQL Normal Seria:
		// SELECT COUNT * FROM raw_body WHERE
		criteria.select(builder.count(root));

		// "COUNT *" só retorna um resultado, "getSingleResult()".
		return manager.createQuery(criteria).getSingleResult();
	}

	// Implementação da PROJEÇÃO de RawBodies:
	@Override
	public Page<ResumoRawBody> resumir(RawBodyFilter rawBodyFilter, Pageable pageable) {
		
		// CriteriaBuilder é quem constroe as criterias.
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		// Retorno para: "ResumoRawBody".
		CriteriaQuery<ResumoRawBody> criteria = builder.createQuery(ResumoRawBody.class);
		// FILTROS (Restrições como o WHERE de SQL normal):
		// root é usado para pegar os atributos de RawBodies que é feito o filtro.
		// "ResumoRawBody" não é uma entidade.
		// Buscar da raiz que é a entidade "RawBody".
		Root<RawBody> root = criteria.from(RawBody.class);
		// Selecionando o que se quer construir.
		criteria.select(builder.construct(ResumoRawBody.class, root.get(RawBody_.id), root.get(RawBody_.app_id)));
		// Cria as retrições.
		Predicate[] predicates = criarRestricoes(rawBodyFilter, builder, root);
		criteria.where(predicates);
		// Criando a Query:
		TypedQuery<ResumoRawBody> query = manager.createQuery(criteria);
		// Adicionar na Query a quantidade total de resultados e onde é o começo.
		// Máximo por página e localização da página.
		adicionarRestricoesDePaginacao(query, pageable);

		// Conteúdo, objeto pageable e o total de elementos.
		return new PageImpl<>(query.getResultList(), pageable, total(rawBodyFilter));
	}
}
