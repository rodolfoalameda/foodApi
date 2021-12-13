package com.project.food.domain.serviceImpl;

import com.project.food.domain.exception.EntidadeEmUsoException;
import com.project.food.domain.exception.RestauranteNaoEncontradaException;
import com.project.food.domain.model.*;
import com.project.food.domain.service.*;
import com.project.food.repository.CozinhaRepository;
import com.project.food.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    public static final String ENTIDADE_EM_USO = "Entidade em uso";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private FormaDePagamentoService formaDePagamentoService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            restauranteRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradaException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(ENTIDADE_EM_USO, id));
        }

    }

    @Override
    @Transactional
    public Restaurante save(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.findOrFail(cozinhaId);
        Cidade cidade = cidadeService.findOrFail(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return restauranteRepository.save(restaurante);
    }


    @Override
    public Restaurante findOrFail(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradaException(id));
    }

    @Override
    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = findOrFail(id);
        restaurante.setAtivo(true);
    }

    @Override
    @Transactional
    public void inativar(Long id) {
        Restaurante restaurante = findOrFail(id);
        restaurante.setAtivo(false);
    }

    @Override
    @Transactional
    public void removerFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = findOrFail(restauranteId);
        FormaPagamento formaPagamento = formaDePagamentoService.findOrFail(formaPagamentoId);
        restaurante.getFormasPagamentos().remove(formaPagamento);
    }

    @Override
    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = findOrFail(restauranteId);
        FormaPagamento formaPagamento = formaDePagamentoService.findOrFail(formaPagamentoId);
        restaurante.getFormasPagamentos().add(formaPagamento);
    }

    @Override
    @Transactional
    public void abrir(Long id) {
        Restaurante restauranteAtual = findOrFail(id);
        restauranteAtual.setAberto(true);
    }

    @Override
    @Transactional
    public void fechar(Long id) {
        Restaurante restauranteAtual = findOrFail(id);
        restauranteAtual.setAberto(false);
    }

    @Override
    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = findOrFail(restauranteId);
        Usuario usuario = usuarioService.findOrFail(usuarioId);
        restaurante.getUsuariosResponsaveis().add(usuario);
    }

    @Override
    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = findOrFail(restauranteId);
        Usuario usuario = usuarioService.findOrFail(usuarioId);
        restaurante.getUsuariosResponsaveis().remove(usuario);
    }
}
