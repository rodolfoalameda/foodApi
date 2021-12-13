package com.project.food.domain.serviceImpl;

import com.project.food.domain.exception.CozinhaNaoEncontradaException;
import com.project.food.domain.model.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.project.food.domain.exception.EntidadeEmUsoException;
import com.project.food.domain.exception.EntidadeNaoEncontradaException;
import com.project.food.domain.model.Cozinha;
import com.project.food.domain.service.CozinhaService;
import com.project.food.repository.CozinhaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CozinhaServiceImpl implements CozinhaService {

	public static final String ENTIDADE_EM_USO = "Entidade em uso";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Override
	@Transactional
	public void delete(Long id) {
		try {
			cozinhaRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(ENTIDADE_EM_USO, id));
		}

	}

	@Override
	@Transactional
	public Cozinha save(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	@Override
	public Cozinha findOrFail(Long id) {
		return cozinhaRepository.findById(id)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(id));
	}

}
