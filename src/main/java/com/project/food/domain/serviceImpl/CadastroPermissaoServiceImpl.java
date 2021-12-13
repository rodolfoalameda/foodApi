package com.project.food.domain.serviceImpl;

import com.project.food.domain.exception.PermissaoNaoEncontradaException;
import com.project.food.domain.model.Grupo;
import com.project.food.domain.model.Permissao;
import com.project.food.domain.service.CadastroPermissaoService;
import com.project.food.domain.service.GrupoService;
import com.project.food.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroPermissaoServiceImpl implements CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private GrupoService grupoService;

    @Override
    public Permissao findOrFail(Long id) {
        return permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(id));
    }
}
