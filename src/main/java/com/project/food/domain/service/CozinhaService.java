package com.project.food.domain.service;

import com.project.food.domain.model.Cozinha;
import com.project.food.domain.model.Estado;

public interface CozinhaService {

	void delete(Long id);

	Cozinha save(Cozinha cozinha);

	Cozinha findOrFail(Long id);

}
