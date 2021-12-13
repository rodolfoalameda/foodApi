package com.project.food.repository.specs;

import com.project.food.domain.model.Pedido;
import com.project.food.repository.filters.PedidoFilters;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilters filtro) {
        return (root, query, builder) -> {

            if(Pedido.class.equals(query.getResultType())){
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }

            var predicates = new ArrayList<Predicate>();

            if (filtro.getClienteId() != null) {
                predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
            }

            if (filtro.getRestauranteId() != null) {
                predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
            }

            if (filtro.getDataCriacao() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacao()));
            }

            if (filtro.getDataFim() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataFim()));
            }

            return builder.and(predicates.toArray(new javax.persistence.criteria.Predicate[0]));
        };
    }
}
