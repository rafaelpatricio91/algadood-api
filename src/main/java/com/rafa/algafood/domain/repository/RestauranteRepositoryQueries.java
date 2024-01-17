package com.rafa.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.rafa.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
}
