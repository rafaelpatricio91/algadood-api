package com.rafa.algafood.api.controller;

import java.util.List;

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
		return estadoRepository.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long id) {
		Estado estado = estadoRepository.buscar(id);
		
		if (estado == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(estado);
	}
	
	@PostMapping
	public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
		Estado estadoSalvo = service.adicionar(estado);
		
		return ResponseEntity.ok(estadoSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Estado> atualizar(@PathVariable("id") Long estadoId, @RequestBody Estado estado) {
		Estado estadoBase = estadoRepository.buscar(estadoId);
		
		if (estadoBase == null) {
			return ResponseEntity.notFound().build();
		}
		
		estado.setId(estadoBase.getId() );
		estadoBase = service.adicionar(estado);
		
		return ResponseEntity.ok(estadoBase);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Estado> remover(@PathVariable("id") Long id) {
		Estado estado = estadoRepository.buscar(id);
		
		if (estado == null) {
			return ResponseEntity.notFound().build();
		}
		
		service.remover(id);
		return ResponseEntity.noContent().build();
	}
	
}
