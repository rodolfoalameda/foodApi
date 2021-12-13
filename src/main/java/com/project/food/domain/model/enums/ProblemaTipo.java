package com.project.food.domain.model.enums;

import lombok.Getter;

@Getter
public enum ProblemaTipo {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade com depedencias"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negocio");

    private String title;
    private String uri;

    ProblemaTipo(String path, String title){
        this.uri = "ifood.com.br" + path;
        this.title = title;
    }

}
