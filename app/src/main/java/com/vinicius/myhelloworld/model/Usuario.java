package com.vinicius.myhelloworld.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.vinicius.myhelloworld.config.ConfiguracaoFireBase;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private int experiencia;
    private String ultimaAula;

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference referenceFirebase = ConfiguracaoFireBase.getFirebase();
        referenceFirebase.child("Usuario").child(getId()).setValue(this);
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getExperiencia() {
        return experiencia % 100;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public String getUltimaAula() {
        return ultimaAula;
    }

    public void setUltimaAula(String ultimaAula) {
        this.ultimaAula = ultimaAula;
    }

    public int getLevel(){
        return experiencia / 100;
    }
}
