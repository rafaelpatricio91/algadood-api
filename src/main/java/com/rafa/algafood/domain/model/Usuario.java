package com.rafa.algafood.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String senha;
	@JsonIgnore
	@Column(nullable = false, columnDefinition = "datetime")
	@CreationTimestamp
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "usuario_grupo", //nome da tabela
			joinColumns = @JoinColumn(name = "usuario_id"), //nome da coluna que representa o id desta classe
			inverseJoinColumns = @JoinColumn(name = "grupo_id")) //nome da coluna que representa o id da outra classe
	private List<Grupo> grupos = new ArrayList<>();

}
