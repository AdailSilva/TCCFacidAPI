package com.adailsilva.tcc.facid.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adailsilva.tcc.facid.api.event.RecursoCriadoEvent;
import com.adailsilva.tcc.facid.api.model.Categoria;
import com.adailsilva.tcc.facid.api.repository.CategoriaRepository;
import com.adailsilva.tcc.facid.api.repository.filter.CategoriaFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoCategoria;
import com.adailsilva.tcc.facid.api.service.CategoriaService;

@RestController // Já converte para JSON como Representação padrão do Recurso.
@RequestMapping("/categorias")
// "@CrossOrigin" Poderia ser para toda a Classe
// @CrossOrigin(maxAge = 10, origins = { "http://localhost:8000" })
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private CategoriaService categoriaService;

	// publisher == Publicador de eventos de aplicação.
	@Autowired
	private ApplicationEventPublisher publisher;

	// [C]RUD - CREATE
	@PostMapping
	// @PreAuthorize("hasAuthority('ROLE_CREATE_CATEGORIA') and
	// #oauth2.hasScope('create')")
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {

		Categoria categoriaSalva = categoriaService.salvar(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	// C[R]UD - READ 1
	@GetMapping
	// Verificando as permissões "ROLES"
	// Acrescentado Scopo.
	// @PreAuthorize("hasAuthority('ROLE_READ_CATEGORIA') and
	// #oauth2.hasScope('read')")
	public List<Categoria> listar() {

		return categoriaRepository.findAll();
	}

	// C[R]UD - READ 2
	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ROLE_READ_CATEGORIA') and
	// #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> buscarPeloId(@PathVariable Long id) {
		Categoria categoria = categoriaRepository.findOne(id);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}

	// C[R]UD - READ 3 (Com Paginação)
	// "Pageable pegeable" para tratamento dos parâmetros de paginação.
	// Ex.: "?size=3&page=0".
	@GetMapping("/pesquisar")
	// @PreAuthorize("hasAuthority('ROLE_READ_CATEGORIA') and
	// #oauth2.hasScope('read')")
	public Page<Categoria> pesquisar(CategoriaFilter categoriaFilter, Pageable pegeable) {

		return categoriaRepository.filtrar(categoriaFilter, pegeable);
	}

	// C[R]UD - READ 4 [ PROJEÇÃO ]
	// "Pageable pegeable" para tratamento dos parâmetros de paginação.
	// Ex.: "?resumo&size=3&page=0".
	@GetMapping(params = "resumo") // este é o parâmentro que diferencia quem o GET irá buscar.
	// @PreAuthorize("hasAuthority('ROLE_READ_CATEGORIA') and
	// #oauth2.hasScope('read')")
	public Page<ResumoCategoria> resumir(CategoriaFilter categoriaFilter, Pageable pegeable) {

		// Passar "pegeable" para frente.
		return categoriaRepository.resumir(categoriaFilter, pegeable);
	}

	// CR[U]D - UPDATE (TOTAL)
	@PutMapping("/{id}")
	// @PreAuthorize("hasAuthority('ROLE_UPDATE_CATEGORIA') and
	// #oauth2.hasScope('update')")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {

		Categoria categoriaSalva = categoriaService.atualizar(id, categoria);
		return ResponseEntity.ok(categoriaSalva);
	}

	// CR[U]D - UPDATE (PARCIAL - NÃO FAZ USO DO VERBO PATCH)
	@PutMapping("/{id}/nome") // NÃO É UM MÉTODO COM PATH PORÉM AINDA É REST.
	@ResponseStatus(HttpStatus.NO_CONTENT) // por não precisar retornar nenhum status HTTP.
	// @PreAuthorize("hasAuthority('ROLE_UPDATE_CATEGORIA') and
	// #oauth2.hasScope('update')")
	public void atualizarPropriedadeNome(@PathVariable Long id, @RequestBody String nome) {

		categoriaService.atualizarPropriedadeNome(id, nome);
	}

	// CRU[D] - DELETE
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // Código HTTP de Retorno: 204.
	// @PreAuthorize("hasAuthority('ROLE_DELETE_CATEGORIA') and
	// #oauth2.hasScope('delete')")
	public void remover(@PathVariable Long id) {

		categoriaRepository.delete(id);
	}
	
	// OBSERVAÇÕES DA CLASSE:
	// CORS "@CrossOrigin", habilitando requisições "GET" de qualquer origem.
	// Default dela é permitir que todas as Origens "Origins" sejam autorizadas a
	// chamar o método, neste caso com um "GET".
	// "maxAge" Tempo máximo para o browser fazer a requisição inicial novamente.
	// "OPTIONS" é uma requisição de "Preflighted requests"
	// para saber se pode enviar uma nova requisição. que será aqui o "GET".
	// "allowedHeaders" pode ser configurado quais verbos aceitar. POST, GET, PUT,
	// PATH, DELETE.
	// Depois de autorizada a requisição de Preflighted espera:
	// "10" segundos para a próxima, tipo fez um "cache".
	// DESATIVEI @CrossOrigin(maxAge = 10, origins = { "http://localhost:8000" })
	// POIS foi implementado um filtro para CORS "globalmente".
	// Posso escolher mais tipos de parâmentros além da origem, etc.
	// SE eu ativar a verificação de permissões para este método com:
	// "@PreAuthorize("hasAuthority('ROLE')")"
	// ENTÃO tenho que desativar esta:
	// http.authorizeRequests().antMatchers("/URI_RECURSO").permitAll() // para
	// "RECURSO" qualquer um pode acessar.
	// @CrossOrigin(maxAge = 10, origins = { "http://localhost:8000" })

}
