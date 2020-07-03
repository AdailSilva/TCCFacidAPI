package com.adailsilva.tcc.facid.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.adailsilva.tcc.facid.api.model.Categoria;
import com.adailsilva.tcc.facid.api.repository.CategoriaRepository;

// Anotação "@Service" diz que a classe será um componente do Spring.
// Para o Spring poder ejetá-la quando preciso.
@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	// [C]RUD - CREATE
	public Categoria salvar(Categoria categoria) {
		
		return categoriaRepository.save(categoria);
	}

	// Não há necessidade de trazer para uma classe de serviço.
	// C[R]UD - READ 1 (Direto do Repository Padrão)
	// C[R]UD - READ 2 (Direto do Repository Padrão)
	// C[R]UD - READ 3 (Com Paginação) (Implementado do Repository Customizado)
	// C[R]UD - READ 4 [ PROJEÇÃO ] (Implementado do Repository Customizado)

	// CR[U]D - UPDATE (TOTAL)
	public Categoria atualizar(Long id, Categoria categoria) {
		
		Categoria categoriaSalva = buscarCategoriaPeloId(id);
		// Classe utilitária do Spring para copiar propriedades de um objeto para outro.
		// source = objeto recebido, target objeto alvo e ignoreProperties para ignorar
		// o que não se quer copiar.
		BeanUtils.copyProperties(categoria, categoriaSalva, "id");
		return categoriaRepository.save(categoriaSalva);
	}

	// CR[U]D - UPDATE (PARCIAL - NÃO FAZ USO DO VERBO PATCH)
	public void atualizarPropriedadeNome(Long id, String nome) {
		
		Categoria categoriaSalva = buscarCategoriaPeloId(id);
		categoriaSalva.setNome(nome);
		categoriaRepository.save(categoriaSalva);
	}

	// Não há necessidade de trazer para uma classe de serviço.
	// CRU[D] - DELETE (Direto do Repository Padrão)

	// Método criado para evitar duplicação de código.
	private Categoria buscarCategoriaPeloId(Long id) {
		
		Categoria categoriaSalva = categoriaRepository.findOne(id);
		// Regra para evitar objeto nulo, caso não encontre o recurso pelo id.
		if (categoriaSalva == null) {
			// Lançando uma exceção adequada ao caso:
			// throw new EmptyResultDataAccessException(expectedSize);
			// Hum (1), pois esperava pelo menos um elemento e nada foi encontrado (0).
			throw new EmptyResultDataAccessException(1);
		}
		return categoriaSalva;
	}
}
