package com.project.food.domain.model;

import groups.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "TB_CIDADE")
public class Cidade {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NOME", nullable = false)
	private String nome;

	@Valid
	@ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
	@ManyToOne
	@JoinColumn(name = "estado_id")
	private Estado estado;

}
