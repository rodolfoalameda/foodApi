package com.project.food.domain.serviceImpl;

import com.project.food.domain.exception.NegocioException;
import com.project.food.domain.exception.UsuarioNaoEncontradaException;
import com.project.food.domain.model.Grupo;
import com.project.food.domain.model.Usuario;
import com.project.food.domain.service.GrupoService;
import com.project.food.domain.service.UsuarioService;
import com.project.food.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GrupoService grupoService;

    @Override
    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = findOrFail(id);
        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new NegocioException("Senha não corresponde");
        }
        usuario.setSenha(novaSenha);
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        entityManager.detach(usuario);
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(
                    String.format("Ja existe usuario cadastro com este email %s", usuario.getEmail()));
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findOrFail(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradaException(id));
    }

    @Override
    @Transactional
    public void retirarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = findOrFail(usuarioId);
        Grupo grupo = grupoService.findOrFail(grupoId);
        usuario.getGrupos().remove(grupo);
    }

    @Override
    @Transactional
    public void adicionarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = findOrFail(usuarioId);
        Grupo grupo = grupoService.findOrFail(grupoId);
        usuario.getGrupos().add(grupo);
    }
}
