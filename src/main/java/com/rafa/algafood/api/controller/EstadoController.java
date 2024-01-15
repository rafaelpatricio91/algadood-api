package com.rafa.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.algafood.domain.model.Estado;
import com.rafa.algafood.domain.repository.EstadoRepository;
import com.rafa.algafood.domain.service.EstadoService;

@RestController 
@RequestMapping(value = "/estados") //,produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private EstadoService service;

	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		
		if (estado.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(estado.get());
	}
	
	@PostMapping
	public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
		Estado estadoSalvo = service.adicionar(estado);
		
		return ResponseEntity.ok(estadoSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Estado> atualizar(@PathVariable("id") Long estadoId, @RequestBody Estado estado) {
		Optional<Estado> estadoBase = estadoRepository.findById(estadoId);
		
		if (estadoBase.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		estado.setId(estadoBase.get().getId() );
		Estado estadoAtualizado = service.adicionar(estado);
		
		return ResponseEntity.ok(estadoAtualizado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Estado> remover(@PathVariable("id") Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		
		if (estado.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		service.remover(id);
		return ResponseEntity.noContent().build();
	}
	
}
