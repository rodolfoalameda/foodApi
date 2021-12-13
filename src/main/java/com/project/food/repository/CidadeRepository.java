package com.project.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.food.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{

}
