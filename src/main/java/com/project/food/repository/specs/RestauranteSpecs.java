package com.project.food.repository.specs;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.project.food.domain.model.Restaurante;

public class RestauranteSpecs {

	public static Specification<Restaurante> freteGratis() {
		return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

	public static Specification<Restaurante> comNome(String nome) {
		return (root, query, builder) -> builder.like(root.get("nome"), "%" + nome + "%");
	}

}
