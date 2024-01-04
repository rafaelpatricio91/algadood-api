package com.rafa.algafood.domain.service;

import java.util.List;

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
		return repository.listar();
	}
	
	public Cidade buscarPorId(Long id) {
		return repository.buscar(id);
	}
	
	@Transactional
	public Cidade cadastrar(Cidade cidade) {
		if (cidade == null) {
			return null;
		}
		
		return repository.salvar(cidade);
	}
	
	@Transactional
	public Cidade atualizar(Cidade cidade) {
		Cidade cidadeBase = repository.buscar(cidade.getId() );
		
		if (cidadeBase == null) {
			return null;
		}
		
		return repository.salvar(cidadeBase);
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			Cidade cidadeBase = repository.buscar(id);
			if (cidadeBase == null) {
				throw new EmptyResultDataAccessException(1);
			}
			repository.remover(cidadeBase);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cidade de codigo %d nao pode ser encontrada", id));
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException( 
					String.format("Cidade de codigo %d nao pode ser removida pois esta em uso", id));
		}
	}

}
