package com.creativityloop.android.temperocapixaba.model;

import java.util.UUID;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Restaurante {

    private UUID mId;
    private String mNome;
    private String mEndereco;

    public String getNome() {
        return mNome;
    }

    public void setNome(String nome) {
        mNome = nome;
    }

    public String getEndereco() {
        return mEndereco;
    }

    public void setEndereco(String endereco) {
        mEndereco = endereco;
    }
}
