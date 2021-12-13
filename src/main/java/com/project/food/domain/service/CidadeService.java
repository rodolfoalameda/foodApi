package com.project.food.domain.service;

import com.project.food.domain.model.Cidade;
import com.project.food.domain.model.Estado;

public interface CidadeService {

	void delete(Long id);

	Cidade save(Cidade cozinha);

	Cidade findOrFail(Long id);
}
