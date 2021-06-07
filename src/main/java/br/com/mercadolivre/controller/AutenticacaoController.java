package br.com.mercadolivre.controller;

import br.com.mercadolivre.config.security.TokenService;
import br.com.mercadolivre.dto.TokenDto;
import br.com.mercadolivre.dto.request.LoginDTORequest;
import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
//@Profile(value = {"prod", "test"})
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;


	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginDTORequest loginDTORequest) {
		UsernamePasswordAuthenticationToken dadosLogin = loginDTORequest.converter();

		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
