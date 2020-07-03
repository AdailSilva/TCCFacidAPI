package com.adailsilva.tcc.facid.api.resource;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.adailsilva.tcc.facid.api.model.RawBody;
import com.adailsilva.tcc.facid.api.repository.RawBodyRepository;
import com.adailsilva.tcc.facid.api.repository.filter.RawBodyFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoRawBody;
import com.adailsilva.tcc.facid.api.service.RawBodyService;

@RestController // Já converte para JSON como Representação padrão do Recurso.
@RequestMapping("/rawbodies")
// "@CrossOrigin" Poderia ser para toda a Classe
// @CrossOrigin(maxAge = 10, origins = { "http://localhost:8000" })
public class RawBodyResources {

	@Autowired
	private RawBodyRepository rawBodyRepository;

	@Autowired
	private RawBodyService rawBodyService;

	// publisher == Publicador de eventos de aplicação.
	@Autowired
	private ApplicationEventPublisher publisher;

	// [C]RUD - CREATE
	@PostMapping
	// @PreAuthorize("hasAuthority('ROLE_CREATE_RAWBODY') and
	// #oauth2.hasScope('create')")
	public ResponseEntity<RawBody> criar(@Valid @RequestBody RawBody rawBody, HttpServletResponse response) {
		
		RawBody rawBodySalvo = rawBodyService.salvar(rawBody);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, rawBodySalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(rawBodySalvo);
	}

	// C[R]UD - READ 1
	@GetMapping
	// @PreAuthorize("hasAuthority('ROLE_READ_RAWBODY') and
	// #oauth2.hasScope('read')")
	public List<RawBody> listar() {
		
		return rawBodyRepository.findAll();
	}

	// C[R]UD - READ 2
	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ROLE_READ_RAWBODY') and
	// #oauth2.hasScope('read')")
	public ResponseEntity<RawBody> buscarPeloId(@PathVariable Long id) {
		
		RawBody rawBody = rawBodyRepository.findOne(id);
		return rawBody != null ? ResponseEntity.ok(rawBody) : ResponseEntity.notFound().build();
	}

	// C[R]UD - READ 3 (Com Paginação)
	// "Pageable pegeable" para tratamento dos parâmetros de paginação.
	// Ex.: "?size=3&page=0".
	@GetMapping("/pesquisar")
	// @PreAuthorize("hasAuthority('ROLE_READ_RAWBODY') and
	// #oauth2.hasScope('read')")
	public Page<RawBody> pesquisar(RawBodyFilter rawBodyFilter, Pageable pegeable) {
		
		// Passar "pegeable" para frente.
		return rawBodyRepository.filtrar(rawBodyFilter, pegeable);
	}

	// C[R]UD - READ 4 [ PROJEÇÃO ]
	// "Pageable pegeable" para tratamento dos parâmetros de paginação.
	// Ex.: "?resumo&size=3&page=0".
	@GetMapping(params = "resumo") // este é o parâmentro que diferencia quem o GET irá buscar.
	// @PreAuthorize("hasAuthority('ROLE_READ_RAWBODY') and
	// #oauth2.hasScope('read')")
	public Page<ResumoRawBody> resumir(RawBodyFilter rawBodyFilter, Pageable pegeable) {
		
		// Passar "pegeable" para frente.
		return rawBodyRepository.resumir(rawBodyFilter, pegeable);
	}

	// CR[U]D - UPDATE (TOTAL)
	@PutMapping("/{id}")
	// @PreAuthorize("hasAuthority('ROLE_UPDATE_RAWBODY') and
	// #oauth2.hasScope('update')")
	public ResponseEntity<RawBody> atualizar(@PathVariable Long id, @Valid @RequestBody RawBody rawBody) {
		
		RawBody rawBodySalvo = rawBodyService.atualizar(id, rawBody);
		return ResponseEntity.ok(rawBodySalvo);
	}

	// CR[U]D - UPDATE (PARCIAL - NÃO FAZ USO DO VERBO PATCH)
	@PutMapping("/{id}/isretry") // NÃO É UM MÉTODO COM PATH PORÉM AINDA É REST.
	@ResponseStatus(HttpStatus.NO_CONTENT) // por não precisar retornar nenhum status HTTP.
	// @PreAuthorize("hasAuthority('ROLE_UPDATE_RAWBODY') and
	// #oauth2.hasScope('update')")
	public void atualizarPropriedadeIsRetry(@PathVariable Long id, @RequestBody Boolean is_retry) {
		
		rawBodyService.atualizarPropriedadeIsRetry(id, is_retry);
	}

	// CRU[D] - DELETE
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // Código HTTP de Retorno: 204.
	// @PreAuthorize("hasAuthority('ROLE_DELETE_RAWBODY') and
	// #oauth2.hasScope('delete')")
	public void remover(@PathVariable Long id) {
		rawBodyRepository.delete(id);
	}

	// C[R]UD (Método auxiliar para consultas importantes)
	@GetMapping("/lastupdown")
	public ResponseEntity<?> buscarUltimosUpEDown(@Valid @RequestBody RawBody jsonRawBody,
			HttpServletResponse response) {

		List<RawBody> rawBodies = rawBodyService.upDownPacotes(jsonRawBody);

		return rawBodies != null ? ResponseEntity.ok(rawBodies) : ResponseEntity.notFound().build(); // 404.
	}

	// ===============================================================================
	// ====================== TTN -> API -> TTN <- API <- TTN ======================
	// ===============================================================================

	// JSON RAW DATA:
	@PostMapping("/uplinks/jsons")
	public String mostrarJsonRawDataUplink(@Valid @RequestBody String json, HttpServletResponse response) {
		
		System.out.println(json);
		return json;
	}

	// === Uplink ===
	@PostMapping("/uplinks")
	public ResponseEntity<?> postUplink(@Valid @RequestBody RawBody rawBody, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("");
		System.out.println("");
		System.out.println("========================================================");
		System.out.println("--------------------------------------------------------");
		System.out.println("UPLINK: |SENSOR|->|MOTE|->|GATEWAY|->|TTN|->|API|->|APP|");
		System.out.println("--------------------------------------------------------");
		System.out.println("========================================================");

		RawBody rawBodySalvo = rawBodyService.uplink(rawBody);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, rawBodySalvo.getId()));
		return rawBodySalvo != null ? ResponseEntity.ok(rawBodySalvo) : ResponseEntity.notFound().build(); // 404.
	}

	// === Downlink ===
	@PostMapping("/downlinks")
	public ResponseEntity<?> postDownlink(@Valid @RequestBody RawBody rawBody, HttpServletResponse response)
			throws IOException {

		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("===========================================================");
		System.out.println("-----------------------------------------------------------");
		System.out.println("DOWNLINK: |APP|->|API|->|TTN|->|GATEWAY|->|MOTE|->|ATUADOR|");
		System.out.println("-----------------------------------------------------------");
		System.out.println("===========================================================");

		RawBody rawBodySalvo = rawBodyService.downlink(rawBody);
		int respostaRequisicao = rawBodySalvo.getHttp_status();
		publisher.publishEvent(new RecursoCriadoEvent(this, response, rawBodySalvo.getId()));
		return respostaRequisicao != 404 ? ResponseEntity.ok(respostaRequisicao) : ResponseEntity.notFound().build(); // 404.
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
