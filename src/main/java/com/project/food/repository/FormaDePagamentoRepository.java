package com.project.food.repository;

import com.project.food.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaDePagamentoRepository extends JpaRepository<FormaPagamento, Long> {
}
