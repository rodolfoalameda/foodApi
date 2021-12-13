package com.project.food.domain.service;

import com.project.food.domain.model.Usuario;

public interface UsuarioService {

    void alterarSenha(Long id, String senhaAtual, String novaSenha);

    Usuario save(Usuario usuario);

    Usuario findOrFail(Long id);

    void retirarGrupo(Long usuarioId, Long grupoId);

    void adicionarGrupo(Long usuarioId, Long grupoId);

}
