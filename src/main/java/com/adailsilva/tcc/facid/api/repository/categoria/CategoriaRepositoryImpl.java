package com.adailsilva.tcc.facid.api.repository.categoria;

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

import com.adailsilva.tcc.facid.api.model.Categoria;
import com.adailsilva.tcc.facid.api.model.Categoria_;
import com.adailsilva.tcc.facid.api.repository.filter.CategoriaFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoCategoria;

// Obrigatório iniciar com o mesmo nome da classe de repositório.
public class CategoriaRepositoryImpl implements CategoriaRepositoryQuery {
	// Injetando o EntityManager para a Criteria do JPA.
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Categoria> filtrar(CategoriaFilter categoriaFilter, Pageable pageable) {
		
		// CriteriaBuilder é quem constroe as criterias.
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		// Retorno para: "Categoria"
		CriteriaQuery<Categoria> criteria = builder.createQuery(Categoria.class);
		// FILTROS (Restrições como o WHERE de SQL normal):
		// root é usado para pegar os atributos de Categoria que é feito o filtro.
		// Buscar da raiz que é a entidade "Categoria".
		Root<Categoria> root = criteria.from(Categoria.class);
		// Criar método: "criarRestricoes()".
		Predicate[] predicates = criarRestricoes(categoriaFilter, builder, root);
		criteria.where(predicates);
		// Criando a Query:
		TypedQuery<Categoria> query = manager.createQuery(criteria);
		// Adicionar na Query a quantidade total de resultados e onde é o começo.
		// Máximo por página e localização da página.
		adicionarRestricoesDePaginacao(query, pageable);
		// Conteúdo, objeto pageable e o total de elementos.
		return new PageImpl<>(query.getResultList(), pageable, total(categoriaFilter));
	}

	// Lista de predicates para ser retornada;
	// Será criada baseado no que foi informado pelo filtro. que é opcional.
	// Não é necessário passar todos os parâmetros.
	private Predicate[] criarRestricoes(CategoriaFilter categoriaFilter, CriteriaBuilder builder,
			Root<Categoria> root) {
		
		// Array de Tamanho variável "Predicate[]" será alimentado
		// Conforme o que foi informado (não nulo).
		List<Predicate> predicates = new ArrayList<>(); // ele será retornado após preenchido.
		// Se for diferente de nulo ele será acrescentado aos critérios.
		// Será considerado apenas os parâmetros informados.
		if (!StringUtils.isEmpty(categoriaFilter.getNome())) {
			// Equivale no SQL "WHERE" descricao "LIKE"... (apenas um Exemplo).
			// se tiver o parâmetro aqui referenciado por exemplo vou add um predicate:
			predicates.add(builder.like(builder.lower(root.get(Categoria_.nome)),
					"%" + categoriaFilter.getNome().toLowerCase() + "%"));
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
	
	private Long total(CategoriaFilter categoriaFilter) {
		
		// Calcular a quantidade de elementos totais para o filtro.
		// nova consulta com a CRITERIA do JPA:
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		// Retorna o tipo Long já foi tipo Categoria.
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		// Retorno "Categoria":
		Root<Categoria> root = criteria.from(Categoria.class);
		// Acrescentando o filtro.
		Predicate[] predicates = criarRestricoes(categoriaFilter, builder, root);
		criteria.where(predicates);
		// No SQL Normal Seria:
		// SELECT COUNT * FROM categoria WHERE
		criteria.select(builder.count(root));

		// "COUNT *" só retorna um resultado, "getSingleResult()".
		return manager.createQuery(criteria).getSingleResult();
	}

	// Implementação da PROJEÇÃO de Categorias:
	@Override
	public Page<ResumoCategoria> resumir(CategoriaFilter categoriaFilter, Pageable pageable) {
		
		// CriteriaBuilder é quem constroe as criterias.
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		// Retorno para: "ResumoCategoria"
		CriteriaQuery<ResumoCategoria> criteria = builder.createQuery(ResumoCategoria.class);
		// FILTROS (Restrições como o WHERE de SQL normal):
		// root é usado para pegar os atributos de Categorias que é feito o filtro.
		// "ResumoCategoria" não é uma entidade.
		// Buscar da raiz que é a entidade "Categoria".
		Root<Categoria> root = criteria.from(Categoria.class);
		// Selecionando o que se quer construir.
		criteria.select(builder.construct(ResumoCategoria.class, root.get(Categoria_.id), root.get(Categoria_.nome)));
		// Cria as retrições.
		Predicate[] predicates = criarRestricoes(categoriaFilter, builder, root);
		criteria.where(predicates);
		// Criando a Query:
		TypedQuery<ResumoCategoria> query = manager.createQuery(criteria);
		// Adicionar na Query a quantidade total de resultados e onde é o começo.
		// Máximo por página e localização da página.
		adicionarRestricoesDePaginacao(query, pageable);

		// Conteúdo, objeto pageable e o total de elementos.
		return new PageImpl<>(query.getResultList(), pageable, total(categoriaFilter));
	}
}
