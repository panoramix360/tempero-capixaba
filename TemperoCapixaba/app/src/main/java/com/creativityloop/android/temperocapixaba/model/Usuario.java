package com.creativityloop.android.temperocapixaba.model;

import java.util.GregorianCalendar;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Usuario {

    public enum TipoUsuario {
        VENDEDOR,COMPRADOR
    }

    private TipoUsuario mTipoUsuario;
    private String mNome;
    private String mEndereco;
    private GregorianCalendar mHorarioAlmoco;

    public TipoUsuario getTipoUsuario() {
        return mTipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        mTipoUsuario = tipoUsuario;
    }

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

    public GregorianCalendar getHorarioAlmoco() {
        return mHorarioAlmoco;
    }

    public void setHorarioAlmoco(GregorianCalendar horarioAlmoco) {
        mHorarioAlmoco = horarioAlmoco;
    }
}