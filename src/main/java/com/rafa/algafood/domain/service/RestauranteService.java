package com.rafa.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rafa.algafood.domain.exception.EntidadeEmUsoException;
import com.rafa.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.rafa.algafood.domain.model.Cozinha;
import com.rafa.algafood.domain.model.Restaurante;
import com.rafa.algafood.domain.repository.CozinhaRepository;
import com.rafa.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format("Restaurante de codigo %d nao pode ser encontrado - id: ", cozinhaId)) );
 
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}
	
	public void excluir(Long id) {
		try {
			restauranteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("Restaurante de codigo %d nao pode ser encontrado - id: ", id));
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException( 
					String.format("Restaurante de codigo %d nao pode ser removido pois esta em uso - id: ", id));
		}
	}
	
	public Restaurante atualizar(Restaurante restaurante) {
		return restauranteRepository.save(restaurante);
	}
}
