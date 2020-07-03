package com.adailsilva.tcc.facid.api.security.jwt;

import lombok.Getter;
import lombok.Setter;

public class AccountCredentials {

	@Getter
	@Setter
	private String usuario;

	@Getter
	@Setter
	private String senha;

}
