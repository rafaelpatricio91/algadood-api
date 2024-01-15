package com.rafa.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rafa.algafood.domain.exception.EntidadeEmUsoException;
import com.rafa.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.rafa.algafood.domain.model.Cozinha;
import com.rafa.algafood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void excluir(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cozinha de codigo %d nao pode ser encontrada - id: ", id));
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException( 
					String.format("Cozinha de codigo %d nao pode ser removida pois esta em uso - id: ", id));
		}
	}
}
