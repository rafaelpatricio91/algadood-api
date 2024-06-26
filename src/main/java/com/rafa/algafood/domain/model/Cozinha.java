package com.rafa.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cozinha")
public class Cozinha {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("titulo")
	@Column(nullable = false)
	private String nome;
	
	@JsonIgnore //para evitar referencia circular
	@OneToMany(mappedBy = "cozinha") //nome do atributo que representa a Cozinha lá na classe Restaurante
	private List<Restaurante> restaurantes = new ArrayList<>();
	
	public Cozinha() {}
	
	public Cozinha(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
}
