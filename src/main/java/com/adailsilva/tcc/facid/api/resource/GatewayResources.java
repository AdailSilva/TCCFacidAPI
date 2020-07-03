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
import com.adailsilva.tcc.facid.api.model.Gateway;
import com.adailsilva.tcc.facid.api.repository.GatewayRepository;
import com.adailsilva.tcc.facid.api.repository.filter.GatewayFilter;
import com.adailsilva.tcc.facid.api.repository.projection.ResumoGateway;
import com.adailsilva.tcc.facid.api.service.GatewayService;

@RestController // Já converte para JSON como Representação padrão do Recurso.
@RequestMapping("/gateways")
// "@CrossOrigin" Poderia ser para toda a Classe
// @CrossOrigin(maxAge = 10, origins = { "http://localhost:8000" })
public class GatewayResources {

	@Autowired
	private GatewayRepository gatewayRepository;

	@Autowired
	private GatewayService gatewayService;

	// publisher == Publicador de eventos de aplicação.
	@Autowired
	private ApplicationEventPublisher publisher;

	// [C]RUD - CREATE
	@PostMapping
	// @PreAuthorize("hasAuthority('ROLE_CREATE_GATEWAY') and
	// #oauth2.hasScope('create')")
	public ResponseEntity<Gateway> criar(@Valid @RequestBody Gateway gateway, HttpServletResponse response) {
		
		Gateway gatewaySalvo = gatewayService.salvar(gateway);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, gatewaySalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(gatewaySalvo);
	}

	// C[R]UD - READ 1
	@GetMapping
	// @PreAuthorize("hasAuthority('ROLE_READ_GATEWAY') and
	// #oauth2.hasScope('read')")
	public List<Gateway> listar() {
		
		return gatewayRepository.findAll();
	}

	// C[R]UD - READ 2
	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ROLE_READ_GATEWAY') and
	// #oauth2.hasScope('read')")
	public ResponseEntity<Gateway> buscarPeloId(@PathVariable Long id) {
		
		Gateway gateway = gatewayRepository.findOne(id);
		return gateway != null ? ResponseEntity.ok(gateway) : ResponseEntity.notFound().build();
	}

	// C[R]UD - READ 3 (Com Paginação)
	// "Pageable pegeable" para tratamento dos parâmetros de paginação.
	// Ex.: "?size=3&page=0".
	@GetMapping("/pesquisar")
	// @PreAuthorize("hasAuthority('ROLE_READ_GATEWAY') and
	// #oauth2.hasScope('read')")
	public Page<Gateway> pesquisar(GatewayFilter gatewayFilter, Pageable pegeable) {
		
		// Passar "pegeable" para frente.
		return gatewayRepository.filtrar(gatewayFilter, pegeable); // Refatoração após implementação
																	// do Filtro.
	}

	// C[R]UD - READ 4 [ PROJEÇÃO ]
	// "Pageable pegeable" para tratamento dos parâmetros de paginação.
	// Ex.: "?resumo&size=3&page=0".
	@GetMapping(params = "resumo") // este é o parâmentro que diferencia quem o GET irá buscar.
	// @PreAuthorize("hasAuthority('ROLE_READ_GATEWAY') and
	// #oauth2.hasScope('read')")
	public Page<ResumoGateway> resumir(GatewayFilter gatewayFilter, Pageable pegeable) {
		
		// Passar "pegeable" para frente.
		return gatewayRepository.resumir(gatewayFilter, pegeable);
	}

	// CR[U]D - UPDATE (TOTAL)
	@PutMapping("/{id}")
	// @PreAuthorize("hasAuthority('ROLE_UPDATE_GATEWAY') and
	// #oauth2.hasScope('update')")
	public ResponseEntity<Gateway> atualizar(@PathVariable Long id, @Valid @RequestBody Gateway gateway) {
		
		Gateway gatewaySalvo = gatewayService.atualizar(id, gateway);
		return ResponseEntity.ok(gatewaySalvo);
	}

	// CR[U]D - UPDATE (PARCIAL - NÃO FAZ USO DO VERBO PATCH)
	@PutMapping("/{id}/time") // NÃO É UM MÉTODO COM PATH PORÉM AINDA É REST.
	@ResponseStatus(HttpStatus.NO_CONTENT) // por não precisar retornar nenhum status HTTP.
	// @PreAuthorize("hasAuthority('ROLE_UPDATE_GATEWAY') and
	// #oauth2.hasScope('update')")
	public void atualizarPropriedadeTime(@PathVariable Long id, @RequestBody String time) {
		
		gatewayService.atualizarPropriedadeTime(id, time);
	}

	// CRU[D] - DELETE
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // Código HTTP de Retorno: 204.
	// @PreAuthorize("hasAuthority('ROLE_DELETE_GATEWAY') and
	// #oauth2.hasScope('delete')")
	public void remover(@PathVariable Long id) {
		
		gatewayRepository.delete(id);
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
