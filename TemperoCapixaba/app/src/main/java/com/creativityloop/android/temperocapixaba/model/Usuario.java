package com.creativityloop.android.temperocapixaba.model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Usuario extends SugarRecord {

    public enum TipoUsuario {
        VENDEDOR,COMPRADOR;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public TipoUsuario mTipoUsuario;
    public String mNome;
    public String mEndereco;
    public GregorianCalendar mHorarioAlmoco;

    public Usuario() {}

    public Usuario(TipoUsuario tipoUsuario, String nome, String endereco, GregorianCalendar horarioAlmoco) {
        this.mTipoUsuario = tipoUsuario;
        this.mNome = nome;
        this.mEndereco = endereco;
        this.mHorarioAlmoco = horarioAlmoco;
    }
}