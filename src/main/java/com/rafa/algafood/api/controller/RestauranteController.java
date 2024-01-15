package com.rafa.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	private ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
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
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Restaurante restaurante) {
		
		try {
			validaCozinhaExistente(restaurante);
			
			Optional<Restaurante> restauranteBusca = restauranteRepository.findById(id);
			
			if (restauranteBusca.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			BeanUtils.copyProperties(restaurante, restauranteBusca.get(), "id");
			restaurante = restauranteRepository.save(restauranteBusca.get());
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return ResponseEntity.ok(restaurante);
		
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);
		
		if (restauranteAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual.get());
		
		return atualizar(id, restauranteAtual.get());
	}
	
	
	private void validaCozinhaExistente(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);

		if (cozinha.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Nao existe cadastro de cozinha com codigo " + cozinhaId);
		}
	}
	
	private void validaRestauranteExistente(Restaurante restaurante) {
		Optional<Restaurante> restauranteBusca = restauranteRepository.findById(restaurante.getId());

		if (restauranteBusca.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Nao existe cadastro de restaurante com codigo " + restaurante.getId());
		}
	}
	
	//Nao foi implementado completamente 
	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		camposOrigem.forEach( (nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			ReflectionUtils.setField(field, restauranteDestino, valorPropriedade);
		});
	}
}
