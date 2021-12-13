package com.project.food.controller;

import com.project.food.controller.assembler.GrupoDTOAssembler;
import com.project.food.controller.assembler.GrupoInputDisassembler;
import com.project.food.controller.assembler.PermissaoDTOAssembler;
import com.project.food.domain.model.Grupo;
import com.project.food.domain.model.dtos.GrupoDTO;
import com.project.food.domain.model.dtos.PermissaoDTO;
import com.project.food.domain.model.input.GrupoInput;
import com.project.food.domain.service.CadastroPermissaoService;
import com.project.food.domain.service.GrupoService;
import com.project.food.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;

    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @Autowired
    private PermissaoDTOAssembler permissaoDTOAssembler;

    @Autowired
    private CadastroPermissaoService cadastroPermissaoService;

    @GetMapping
    public List<GrupoDTO> findAll() {
        List<Grupo> list = grupoRepository.findAll();
        return grupoDTOAssembler.fromDomainsToDtos(list);
    }

    @GetMapping("/{id}")
    public GrupoDTO findById(@PathVariable Long id) {
        Grupo grupo = grupoService.findOrFail(id);
        return grupoDTOAssembler.fromDomainToDto(grupo);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO save(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.fromModelToDomain(grupoInput);
        grupo = grupoService.save(grupo);
        return grupoDTOAssembler.fromDomainToDto(grupo);
    }

    @PutMapping("/{id}")
    @Transactional
    public GrupoDTO update(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = grupoService.findOrFail(id);
        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
        grupoAtual = grupoService.save(grupoAtual);
        return grupoDTOAssembler.fromDomainToDto(grupoAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        grupoService.delete(id);
    }

    @GetMapping("/{grupoId}/permissoes")
    public List<PermissaoDTO> findAllPermissoes(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.findOrFail(grupoId);
        return permissaoDTOAssembler.fromDomainsToDtos(grupo.getPermissoes());
    }

    @DeleteMapping("/{grupoId}/permissoes/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.removerPermissao(grupoId, permissaoId);
    }

    @PutMapping("/{grupoId}/permissoes/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.adicionarPermissao(grupoId, permissaoId);
    }
}
