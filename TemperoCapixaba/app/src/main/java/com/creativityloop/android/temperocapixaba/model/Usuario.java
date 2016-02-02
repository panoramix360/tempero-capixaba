package com.creativityloop.android.temperocapixaba.model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Usuario extends SugarRecord {

    public enum TIPO_ENTREGA {
        ENTREGA_ENDERECO, BUSCAR_LOCAL
    }

    public String mNome;
    public String mEndereco;
    public String mTelefone;
    public String mEmail;
    public String mEmpresa;
    public TIPO_ENTREGA mTipoEntrega;

    public Usuario() {}

    public Usuario(String nome, String endereco, String telefone, String email, String empresa, TIPO_ENTREGA tipoEntrega) {
        this.mNome = nome;
        this.mEndereco = endereco;
        this.mTelefone = telefone;
        this.mEmail = email;
        this.mEmpresa = empresa;
        this.mTipoEntrega = tipoEntrega;
    }
}