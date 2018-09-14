package com.vinicius.myhelloworld.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {

    private int id;
    private String nome;
    private String aula;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
