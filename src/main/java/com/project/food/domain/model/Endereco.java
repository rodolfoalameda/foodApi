package com.project.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable
public class Endereco {

	@Column(name = "cep")
	private String cep;

	@Column(name = "logradouro")
	private String logradouro;

	@Column(name = "numero_endereco")
	private String numero;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "bairro")
	private String bairro;

	@ManyToOne
	@JoinColumn(name = "endereco_cidade_id")
	private Cidade cidade;

}
