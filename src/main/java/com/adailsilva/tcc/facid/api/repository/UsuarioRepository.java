package com.adailsilva.tcc.facid.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adailsilva.tcc.facid.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	// Buscar Usuários com SQL Nativo: ("?1", onde 1 é o índice do parâmetro)
	@Query(value = "SELECT * FROM tccfacidapi.usuarios", nativeQuery = true)
	List<Usuario> listarUsuarios();

	// "Optional" se não encontrar não precisa ficar verificando se é nulo.
	// um dos motivos para deixar a permissão no model como:
	// "fetch = FetchType.EAGER"
	public Optional<Usuario> findByEmail(String email);

}
