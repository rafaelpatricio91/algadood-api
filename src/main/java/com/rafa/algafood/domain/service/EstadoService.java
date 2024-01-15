package com.rafa.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafa.algafood.domain.exception.EntidadeEmUsoException;
import com.rafa.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.rafa.algafood.domain.model.Estado;
import com.rafa.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repository;

	@Transactional
	public Estado adicionar(Estado estado) {
		return repository.save(estado);
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
				String.format("Estado de codigo %d nao pode ser encontrado", id));
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException( 
				String.format("Estado de codigo %d nao pode ser removido pois esta em uso", id));
		}
	}
	
	public Estado atualizar(Estado estado) {
		return repository.save(estado);
	}
}
