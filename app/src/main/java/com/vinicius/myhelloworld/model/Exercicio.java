package com.vinicius.myhelloworld.model;

import java.io.Serializable;

public class Exercicio implements Serializable {

    private String questao;

    public String getQuestao() {
        return questao;
    }

    public void setQuestao(String questao) {
        this.questao = questao;
    }
}
