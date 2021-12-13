package com.project.food.domain.serviceImpl;

import com.project.food.domain.exception.CidadeNaoEncontradaException;
import com.project.food.domain.model.Estado;
import com.project.food.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.project.food.domain.exception.EntidadeEmUsoException;
import com.project.food.domain.exception.EntidadeNaoEncontradaException;
import com.project.food.domain.model.Cidade;
import com.project.food.domain.service.CidadeService;
import com.project.food.repository.CidadeRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CidadeServiceImpl implements CidadeService {

	public static final String ENTIDADE_EM_USO = "Entidade em uso";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoService estadoService;

	@Override
	@Transactional
	public void delete(Long id) {
		try {
			cidadeRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(ENTIDADE_EM_USO, id));
		}
	}

	@Override
	@Transactional
	public Cidade save(Cidade cidade){
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoService.findOrFail(estadoId);
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}

	@Override
	public Cidade findOrFail(Long id) {
		return cidadeRepository.findById(id)
				.orElseThrow(() -> new CidadeNaoEncontradaException(id));
	}

}
