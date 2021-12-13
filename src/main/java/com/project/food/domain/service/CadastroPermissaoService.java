package com.project.food.domain.service;

import com.project.food.domain.model.Permissao;

public interface CadastroPermissaoService {

    Permissao findOrFail(Long id);

}
