package com.vinicius.myhelloworld.model;

import java.io.Serializable;

public class Aula implements Serializable {
    private String nome;
    private String aula;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }
}
