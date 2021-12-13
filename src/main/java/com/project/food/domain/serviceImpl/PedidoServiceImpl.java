package com.project.food.domain.serviceImpl;

import com.project.food.domain.exception.NegocioException;
import com.project.food.domain.exception.PedidoNaoEncontradaException;
import com.project.food.domain.model.*;
import com.project.food.domain.service.*;
import com.project.food.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaDePagamentoService formaDePagamentoService;

    @Override
    public Pedido findOrFail(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradaException(id));
    }

    @Override
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.valorTotal();

        return pedidoRepository.save(pedido);
    }

    @Override
    public void validarPedido(Pedido pedido) {
        Cidade cidade = cidadeService.findOrFail(pedido.getEndereco().getCidade().getId());
        Usuario cliente = usuarioService.findOrFail(pedido.getCliente().getId());
        Restaurante restaurante = restauranteService.findOrFail(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaDePagamentoService.findOrFail(pedido.getFormaPagamento().getId());

        pedido.getEndereco().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento %s nao é aceita", formaPagamento.getDescricao()));
        }
    }

    @Override
    public void validarItens(Pedido pedido) {
        pedido.getPedido().forEach(item -> {
            Produto produto = produtoService.findOrFail(pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());

        });
    }
}
