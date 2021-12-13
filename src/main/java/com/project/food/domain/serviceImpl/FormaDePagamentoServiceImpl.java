package com.project.food.domain.serviceImpl;

import com.project.food.domain.exception.EntidadeEmUsoException;
import com.project.food.domain.exception.EstadoNaoEncontradaException;
import com.project.food.domain.exception.FormaPagamentoNaoEncontrada;
import com.project.food.domain.model.FormaPagamento;
import com.project.food.domain.service.FormaDePagamentoService;
import com.project.food.repository.FormaDePagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormaDePagamentoServiceImpl implements FormaDePagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento %d esta em uso";

    @Autowired
    private FormaDePagamentoRepository formaDePagamentoRepository;


    @Override
    public void delete(Long id) {
        try {
            formaDePagamentoRepository.deleteById(id);
            formaDePagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontrada(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
        }
    }

    @Override
    @Transactional
    public FormaPagamento save(FormaPagamento formaPagamento) {
        return formaDePagamentoRepository.save(formaPagamento);
    }

    @Override
    public FormaPagamento findOrFail(Long id) {
        return formaDePagamentoRepository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradaException(id));
    }
}
