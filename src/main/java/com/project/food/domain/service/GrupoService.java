package com.project.food.domain.service;

import com.project.food.domain.model.Grupo;

public interface GrupoService {

    void delete(Long id);

    Grupo save(Grupo grupo);

    Grupo findOrFail(Long id);

    Boolean removerPermissao(Long grupoId, Long permissaoId);

    Boolean adicionarPermissao(Long grupoId, Long permissaoId);
}
