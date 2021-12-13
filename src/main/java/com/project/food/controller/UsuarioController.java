package com.project.food.controller;

import com.project.food.controller.assembler.GrupoDTOAssembler;
import com.project.food.controller.assembler.UsuarioDTOAssembler;
import com.project.food.controller.assembler.UsuarioInputDisassembler;
import com.project.food.domain.model.Grupo;
import com.project.food.domain.model.Usuario;
import com.project.food.domain.model.dtos.GrupoDTO;
import com.project.food.domain.model.dtos.UsuarioDTO;
import com.project.food.domain.model.input.SenhaInput;
import com.project.food.domain.model.input.UsuarioComSenhaInput;
import com.project.food.domain.model.input.UsuarioInput;
import com.project.food.domain.service.GrupoService;
import com.project.food.domain.service.UsuarioService;
import com.project.food.repository.GrupoRepository;
import com.project.food.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;

    @GetMapping
    public List<UsuarioDTO> findAll() {
        List<Usuario> list = usuarioRepository.findAll();
        return usuarioDTOAssembler.fromDomainsToDtos(list);
    }

    @GetMapping("/{id}")
    public UsuarioDTO findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findOrFail(id);
        return usuarioDTOAssembler.fromDomainToDto(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO save(@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {
        Usuario usuario = usuarioInputDisassembler.fromModelToDomain(usuarioComSenhaInput);
        usuario = usuarioService.save(usuario);
        return usuarioDTOAssembler.fromDomainToDto(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioDTO update(@PathVariable Long id, @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = usuarioService.findOrFail(id);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = usuarioService.save(usuarioAtual);
        return usuarioDTOAssembler.fromDomainToDto(usuarioAtual);
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senhaInput) {
        usuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }

    @GetMapping("/{usuarioId}/grupos")
    List<GrupoDTO> findAllGrupos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.findOrFail(usuarioId);
        return grupoDTOAssembler.fromDomainsToDtos(usuario.getGrupos());
    }

    @DeleteMapping("/{usuarioId}/grupos/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.retirarGrupo(usuarioId, grupoId);
    }

    @PutMapping("/{usuarioId}/grupos/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.adicionarGrupo(usuarioId, grupoId);
    }

}
