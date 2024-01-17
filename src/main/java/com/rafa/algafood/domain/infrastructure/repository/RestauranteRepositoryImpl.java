package com.rafa.algafood.domain.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.rafa.algafood.domain.model.Restaurante;
import com.rafa.algafood.domain.repository.RestauranteRepositoryQueries;

import lombok.var;


@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {
	
	private EntityManager manager;
	
	
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		var jpql = "from Restaurante where nome like :nome"
				+ "and taxaFrete between :taxaInicial and :taxaFinal";
		
		return manager.createQuery(jpql, Restaurante.class)
				.setParameter("nome", "%"+nome+"%")
				.setParameter("taxaInicial", taxaFreteInicial)
				.setParameter("taxaFinal", taxaFreteFinal).getResultList();
	}

}
