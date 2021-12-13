package com.project.food.domain.serviceImpl;

import com.project.food.domain.exception.EntidadeEmUsoException;
import com.project.food.domain.exception.EstadoNaoEncontradaException;
import com.project.food.domain.exception.GrupoNaoEncontradaException;
import com.project.food.domain.model.Grupo;
import com.project.food.domain.model.Permissao;
import com.project.food.domain.service.CadastroPermissaoService;
import com.project.food.domain.service.GrupoService;
import com.project.food.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrupoServiceImpl implements GrupoService {

    public static final String ENTIDADE_EM_USO = "Entidade em uso";

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroPermissaoService cadastroPermissaoService;

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            grupoRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradaException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(ENTIDADE_EM_USO, id));
        }

    }

    @Override
    public Grupo save(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Override
    public Grupo findOrFail(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradaException(id));
    }

    @Override
    @Transactional
    public Boolean removerPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = findOrFail(grupoId);
        Permissao permissao = cadastroPermissaoService.findOrFail(permissaoId);
        return grupo.getPermissoes().remove(permissao);
    }

    @Override
    @Transactional
    public Boolean adicionarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = findOrFail(grupoId);
        Permissao permissao = cadastroPermissaoService.findOrFail(permissaoId);
        return grupo.getPermissoes().add(permissao);
    }
}
