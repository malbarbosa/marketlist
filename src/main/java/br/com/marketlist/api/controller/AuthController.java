package br.com.marketlist.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marketlist.api.exception.EntityNotFound;
import br.com.marketlist.api.model.UserApp;
import br.com.marketlist.api.request.AuthRequest;
import br.com.marketlist.api.response.JwtResponse;
import br.com.marketlist.api.service.AuthService;
import br.com.marketlist.api.utils.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1/authenticate")
@CrossOrigin
public class AuthController extends AbstractController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private AuthService authService;

	
	@PostMapping
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		Optional<UserApp> userApp = authService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		userApp.orElseThrow(()-> new EntityNotFound());
		final String token = jwtTokenUtil.generateToken(userApp.get());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
