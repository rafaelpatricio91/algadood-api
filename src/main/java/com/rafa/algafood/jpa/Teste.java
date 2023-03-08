package com.rafa.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.rafa.algafood.AlgafoodApiApplication;
import com.rafa.algafood.domain.model.Cozinha;

public class Teste {
	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha ccozinha = appContext.getBean(CadastroCozinha.class);
		
		//LISTA ALL
//		List<Cozinha> cozinhas = ccozinha.listar();
//		
//		for (Cozinha cozinha : cozinhas) {
//			System.out.println(cozinha.getNome());
//		}
		
		//ADICIONAR
//		Cozinha cozinha = new Cozinha();
//		cozinha.setNome("Nordestina");
//		
//		ccozinha.adicionar(cozinha);
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		ccozinha.remover(cozinha);
	}
}
