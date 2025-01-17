package com.ideia.projetoideia.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.dto.JwtRespostaDto;
import com.ideia.projetoideia.model.dto.LoginDto;
import com.ideia.projetoideia.model.dto.LoginTokenDto;
import com.ideia.projetoideia.security.util.JwtUtils;
import com.ideia.projetoideia.services.EquipeService;
import com.ideia.projetoideia.services.utils.GeradorUserToken;

@RestController
@RequestMapping("/ideia/seguranca")
public class ControllerSeguranca {

	@SuppressWarnings("unused")
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private EquipeService equipeService;

	@PostMapping("/login")
	public JwtRespostaDto login(@Valid @RequestBody LoginDto loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getSenha()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		Usuario userDetails = (Usuario) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		JwtRespostaDto resposta = new JwtRespostaDto(jwt, userDetails.getId(), userDetails.getNomeUsuario(),
				userDetails.getUsername(), userDetails.getEmail(), roles);

		return resposta;
	}
	
	@PostMapping("/token")
	public JwtRespostaDto token(@Valid @RequestBody LoginTokenDto loginRequest) throws Exception{
		
		Equipe equipe = equipeService.consultarEquipePorToken(loginRequest.getToken());
				
		if(equipe != null) {
						
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(equipe.getNomeEquipe()+"token@gmail.com", GeradorUserToken.gerarSenha(equipe.getNomeEquipe())));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);
			Usuario userDetails = (Usuario) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());
			
			JwtRespostaDto jwtRespostaDto = new JwtRespostaDto(jwt, userDetails.getId(), userDetails.getNomeUsuario(),
					userDetails.getUsername(), userDetails.getEmail(), roles);
			
			jwtRespostaDto.setIdEquipe(equipe.getId());
			return jwtRespostaDto;

		}
		throw new Exception("Esse token não pertence a nenhuma equipe");
		
	}

}
