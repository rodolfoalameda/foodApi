package com.project.food.domain.serviceImpl;

import com.project.food.domain.model.Pedido;
import com.project.food.domain.model.dtos.VendaDiariaDTO;
import com.project.food.domain.model.enums.StatusPedido;
import com.project.food.domain.service.VendaDiariaService;
import com.project.food.repository.filters.VendaDiariaFilters;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VendaDiariaServiceImpl implements VendaDiariaService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilters filters, String timeOffSet) {

        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiariaDTO.class);
        var root = query.from(Pedido.class);

        var predicates = new ArrayList<Predicate>();

        var funcionConvertTz = builder.function("convert_tz", Date.class, root.get("dataCriacao"),
                builder.literal("+00:00"), builder.literal(timeOffSet));

        var functionDataCriacao = builder.function("date", Date.class, funcionConvertTz);

        var select = builder.construct(VendaDiariaDTO.class,
                functionDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        if (filters.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante"), filters.getRestauranteId()));
        }

        if (filters.getDataCriacao() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filters.getDataCriacao()));
        }

        if (filters.getDataFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filters.getDataFim()));
        }

        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.select(select);
        query.groupBy(functionDataCriacao);
        query.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(query).getResultList();


    }
}
