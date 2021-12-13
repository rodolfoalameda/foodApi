package com.project.food.domain.serviceImpl;

import com.project.food.domain.exception.EntidadeEmUsoException;
import com.project.food.domain.exception.EntidadeNaoEncontradaException;
import com.project.food.domain.exception.EstadoNaoEncontradaException;
import com.project.food.domain.model.Estado;
import com.project.food.domain.service.EstadoService;
import com.project.food.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadoServiceImpl implements EstadoService {

	public static final String ENTIDADE_EM_USO = "Entidade em uso";
	@Autowired
	private EstadoRepository estadoRepository;

	@Override
	@Transactional
	public void delete(Long id) {
		try {
			estadoRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradaException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(ENTIDADE_EM_USO, id));
		}

	}

	@Override
	@Transactional
	public Estado save(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Override
	public Estado findOrFail(Long id) {
		return estadoRepository.findById(id)
				.orElseThrow(() -> new EstadoNaoEncontradaException(id));
	}
}
