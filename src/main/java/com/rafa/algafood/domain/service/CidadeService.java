package com.rafa.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafa.algafood.domain.exception.EntidadeEmUsoException;
import com.rafa.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.rafa.algafood.domain.model.Cidade;
import com.rafa.algafood.domain.repository.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repository;
	
	public List<Cidade> listar() {
		return repository.findAll();
	}
	
	public Optional<Cidade> buscarPorId(Long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public Cidade cadastrar(Cidade cidade) {
		if (cidade == null) {
			return null;
		}
		
		return repository.save(cidade);
	}
	
	@Transactional
	public Cidade atualizar(Cidade cidade) {
		Optional<Cidade> cidadeBase = repository.findById(cidade.getId() );
		
		if (cidadeBase == null) {
			return null;
		}
		
		return repository.save(cidadeBase.get());
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			Optional<Cidade> cidadeBase = repository.findById(id);
			if (cidadeBase.isEmpty()) {
				throw new EmptyResultDataAccessException(1);
			}
			repository.delete(cidadeBase.get());
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cidade de codigo %d nao pode ser encontrada", id));
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException( 
					String.format("Cidade de codigo %d nao pode ser removida pois esta em uso", id));
		}
	}

}
