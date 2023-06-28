package com.vigimod.api.security.payload;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto {
	private String name;
	private String lastname;
	private String username;
	private String email;
	private String password;
	// Passagio di ruoli dal client (Facoltativo)
	private Set<String> roles;
}

// Il client dovrà inviare un oggetto JSON nel body con questa forma
/*
 * { "fullname": "Francesca Neri", "username": "francescaneri", "email":
 * "f.neri@example.com", "password": "qwerty", "roles": ["MODERATOR", "USER"] }
 */
