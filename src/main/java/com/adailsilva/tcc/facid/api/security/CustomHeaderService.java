package com.adailsilva.tcc.facid.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.adailsilva.tcc.facid.api.model.CustomHeader;
import com.adailsilva.tcc.facid.api.repository.CustomHeaderRepository;

@Service
public class CustomHeaderService {

	@Autowired
	private CustomHeaderRepository customHeaderRepository;

	public CustomHeader loadCustomHeaderByLogin(String login) {
		
		// Validará usuário pelo "login".
		CustomHeader customHeader = customHeaderRepository.customHeaderPorLogin(login);
		
		// Regra para evitar objeto nulo, caso não encontre o recurso pelo login.
		if (customHeader == null) {
			// Lançando uma exceção adequada ao caso:
			// throw new EmptyResultDataAccessException(expectedSize);
			// Hum (1), pois esperava pelo menos um elemento e nada foi encontrado (0).
			throw new EmptyResultDataAccessException(1);
		}

		return customHeader;
	}

}
