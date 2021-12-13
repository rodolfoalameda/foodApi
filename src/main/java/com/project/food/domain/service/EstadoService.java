package com.project.food.domain.service;

import com.project.food.domain.model.Estado;

public interface EstadoService {

	void delete(Long id);

	Estado save(Estado estado);

	Estado findOrFail(Long id);
}
