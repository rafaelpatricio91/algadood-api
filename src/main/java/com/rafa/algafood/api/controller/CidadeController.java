package com.rafa.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.rafa.algafood.domain.model.Cidade;
import com.rafa.algafood.domain.model.Estado;
import com.rafa.algafood.domain.repository.EstadoRepository;
import com.rafa.algafood.domain.service.CidadeService;

@RestController
@RequestMapping(value = "cidades")
public class CidadeController {
	
	@Autowired
	private CidadeService service;
	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping
	public List<Cidade> listar() {
		return service.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cidade> buscarPorId(@PathVariable Long id) {
		Optional<Cidade> cidade = service.buscarPorId(id);
		
		if (cidade.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cidade.get());
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Cidade cidade) {
		try {
			validaEstadoExistente(cidade);
			
			Cidade cidadeSalva = service.cadastrar(cidade);
			
			if (cidadeSalva == null) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(cidadeSalva);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
		try {
			validaEstadoExistente(cidade);
			Optional<Cidade> cidadeBase = service.buscarPorId(id);
			if (cidadeBase.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			BeanUtils.copyProperties(cidade, cidadeBase.get(), "id");
			Cidade cidadeAtualizada = service.atualizar(cidadeBase.get());
			
			return ResponseEntity.ok(cidadeAtualizada);
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		try {
			service.remover(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	
	
	private void validaEstadoExistente(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Optional<Estado> estado = estadoRepository.findById(estadoId);

		if (estado.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Nao existe cadastro de estado com codigo " + estadoId);
		}
	}

}
