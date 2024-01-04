package com.rafa.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.rafa.algafood.domain.model.Cozinha;
import com.rafa.algafood.domain.model.Restaurante;
import com.rafa.algafood.domain.repository.CozinhaRepository;
import com.rafa.algafood.domain.repository.RestauranteRepository;
import com.rafa.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.listar();
	}
	
	@GetMapping("/{restauranteId}")
	private ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteRepository.buscar(restauranteId);
		
		if (restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			validaCozinhaExistente(restaurante);
			
			restaurante = restauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> atualizar(@RequestBody Restaurante restaurante) {
		
		try {
			validaCozinhaExistente(restaurante);
			
			Restaurante restauranteBusca = restauranteRepository.buscar(restaurante.getId());
			
			if (restauranteBusca == null) {
				return ResponseEntity.notFound().build();
			}
			
			restaurante = restauranteRepository.salvar(restaurante);
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return ResponseEntity.ok(restaurante);
		
	}
	
	private void validaCozinhaExistente(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException("Nao existe cadastro de cozinha com codigo " + cozinhaId);
		}
	}
	
	private void validaRestauranteExistente(Restaurante restaurante) {
		Restaurante restauranteBusca = restauranteRepository.buscar(restaurante.getId());

		if (restauranteBusca == null) {
			throw new EntidadeNaoEncontradaException("Nao existe cadastro de restaurante com codigo " + restaurante.getId());
		}
	}
}
