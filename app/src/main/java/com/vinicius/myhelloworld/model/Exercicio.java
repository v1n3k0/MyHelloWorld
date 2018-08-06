package com.vinicius.myhelloworld.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Exercicio implements Serializable {

    private int id;
    private String questao;
    private ArrayList<Resposta> respostas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestao() {
        return questao;
    }

    public void setQuestao(String questao) {
        this.questao = questao;
    }

    public ArrayList<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(ArrayList<Resposta> repostas) {
        this.respostas = repostas;
    }
}
