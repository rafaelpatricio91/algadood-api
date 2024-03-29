package com.rafa.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.algafood.api.model.CozinhasXmlWrapper;
import com.rafa.algafood.domain.exception.EntidadeEmUsoException;
import com.rafa.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.rafa.algafood.domain.model.Cozinha;
import com.rafa.algafood.domain.repository.CozinhaRepository;
import com.rafa.algafood.domain.service.CozinhaService;

@RestController //@Controller e @ResponseBody ao mesmo tempo
@RequestMapping(value = "/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaService cozinhaService;	
	
	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {
		return new CozinhasXmlWrapper(cozinhaRepository.findAll() );
	}
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long cozinhaId) {
//		Cozinha cozinha = cozinhaRepository.findById(cozinhaId);
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		
		//return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get()); //Forma mais simplificada da linha anterior
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.LOCATION, "https://NovaUrlTemp");
//		
//		return ResponseEntity
//				.status(HttpStatus.FOUND)
//				.headers(headers)
//				.build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha criar(@RequestBody Cozinha cozinha) {
		return cozinhaService.salvar(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable("cozinhaId") Long cozinhaId,@RequestBody Cozinha cozinha) {
		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
		if (!cozinhaAtual.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
		Cozinha cozinhaSalva = cozinhaService.salvar(cozinhaAtual.get());
		
		return ResponseEntity.ok(cozinhaSalva);
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable("cozinhaId") Long cozinhaId) {
		try {
			cozinhaService.excluir(cozinhaId);
			return ResponseEntity.noContent().build();
			
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
}
