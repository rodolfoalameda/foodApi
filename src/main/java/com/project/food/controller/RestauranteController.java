package com.project.food.controller;

import com.project.food.controller.assembler.*;
import com.project.food.domain.exception.CidadeNaoEncontradaException;
import com.project.food.domain.exception.CozinhaNaoEncontradaException;
import com.project.food.domain.exception.NegocioException;
import com.project.food.domain.model.Produto;
import com.project.food.domain.model.Restaurante;
import com.project.food.domain.model.dtos.FormaPagamentoDTO;
import com.project.food.domain.model.dtos.ProdutoDTO;
import com.project.food.domain.model.dtos.RestauranteDTO;
import com.project.food.domain.model.dtos.UsuarioDTO;
import com.project.food.domain.model.input.ProdutoInput;
import com.project.food.domain.model.input.RestauranteInput;
import com.project.food.domain.model.views.RestauranteView;
import com.project.food.domain.service.ProdutoService;
import com.project.food.domain.service.RestauranteService;
import com.project.food.repository.ProdutoRepository;
import com.project.food.repository.RestauranteRepository;
import com.project.food.repository.specs.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteDTOAssembler restauranteDTOAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Autowired
    private FormaDePagamentoDTOAssembler formaDePagamentoDTOAssembler;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoDTOAssembler produtoDTOAssembler;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;

    @GetMapping("/{id}")
    public RestauranteDTO findById(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.findOrFail(id);
        return restauranteDTOAssembler.fromDomainToDto(restaurante);
    }

//    @GetMapping
//    public List<RestauranteDTO> findAll() {
//        List<Restaurante> list = restauranteRepository.findAll();
//        return restauranteDTOAssembler.fromDomainsToDtos(list);
//    }

    @GetMapping
    public MappingJacksonValue findAll(@RequestParam(required = false) String projecao) {
        List<Restaurante> list = restauranteRepository.findAll();
        List<RestauranteDTO> listDTO = restauranteDTOAssembler.fromDomainsToDtos(list);

        MappingJacksonValue restauranteWrapper = new MappingJacksonValue(listDTO);
        restauranteWrapper.setSerializationView(RestauranteView.Resumo.class);

        if (projecao == null) {
            restauranteWrapper.setSerializationView(null);
        }

        return restauranteWrapper;

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        restauranteService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO save(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.fromModelToDomain(restauranteInput);
            return restauranteDTOAssembler.fromDomainToDto(restauranteService.save(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteDTO edit(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteAtual = restauranteService.findOrFail(id);
            restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
            return restauranteDTOAssembler.fromDomainToDto(restauranteService.save(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Restaurante>> findByTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        List<Restaurante> list = restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/filter2")
    public ResponseEntity<List<Restaurante>> findWithFilter(@RequestParam String nome) {
        List<Restaurante> list = restauranteRepository
                .findAll(RestauranteSpecs.comNome(nome).and(RestauranteSpecs.freteGratis()));
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id) {
        restauranteService.ativar(id);
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        restauranteService.inativar(id);
    }

    @GetMapping("/{id}/formas-pagamento")
    public List<FormaPagamentoDTO> findAll(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.findOrFail(id);
        return formaDePagamentoDTOAssembler.fromDomainsToDtos(restaurante.getFormasPagamentos());

    }

    @DeleteMapping("/{id}/formas-pagamento/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFormaPagamento(@PathVariable Long id, @PathVariable Long formaPagamentoId) {
        restauranteService.removerFormaPagamento(id, formaPagamentoId);
    }

    @PutMapping("/{id}/formas-pagamento/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarFormaPagamento(@PathVariable Long id, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(id, formaPagamentoId);
    }

    @GetMapping("/{restauranteId}/produtos")
    public List<ProdutoDTO> findAllProducts(@PathVariable Long restauranteId,
                                            @RequestParam(required = false) Boolean inativos) {
        Restaurante restaurante = restauranteService.findOrFail(restauranteId);
        List<Produto> list = null;

        if (inativos != null && inativos) {
            list = produtoRepository.findAll();
        } else {
            list = produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return produtoDTOAssembler.fromDomainsToDtos(list);
    }

    @GetMapping("/{restauranteId}/produtos/{produtoId}")
    public ProdutoDTO findProductById(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.findOrFail(restauranteId, produtoId);
        return produtoDTOAssembler.fromDomainToDto(produto);
    }

    @PostMapping("/{restauranteId}/produtos")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO saveProduct(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.findOrFail(restauranteId);
        Produto produto = produtoInputDisassembler.fromModelToDomain(produtoInput);
        produto.setRestaurante(restaurante);
        produto = produtoService.save(produto);
        return produtoDTOAssembler.fromDomainToDto(produto);
    }

    @PutMapping("/{restauranteId}/produtos/{produtoId}")
    public ProdutoDTO updateProduct(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                    @RequestBody @Valid ProdutoInput produtoInput) {

        Produto produtoAtual = produtoService.findOrFail(restauranteId, produtoId);
        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
        produtoAtual = produtoService.save(produtoAtual);
        return produtoDTOAssembler.fromDomainToDto(produtoAtual);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
    }

    @GetMapping("/{restauranteId}/responsaveis")
    public List<UsuarioDTO> findAllUsuariosResponsaveis(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.findOrFail(restauranteId);
        return usuarioDTOAssembler.fromDomainsToDtos(restaurante.getUsuariosResponsaveis());
    }

    @DeleteMapping("/{restauranteId}/responsaveis/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.desassociarResponsavel(restauranteId, responsavelId);
    }

    @PutMapping("/{restauranteId}/responsaveis/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.associarResponsavel(restauranteId, responsavelId);
    }

}
