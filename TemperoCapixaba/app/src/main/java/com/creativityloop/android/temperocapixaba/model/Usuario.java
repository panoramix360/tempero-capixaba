package com.creativityloop.android.temperocapixaba.model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Usuario extends SugarRecord {

    public enum TIPO_ENTREGA {
        ENTREGA_ENDERECO(1), BUSCAR_LOCAL(2);

        private final int value;
        TIPO_ENTREGA(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public int mId;
    public String mNome;
    public String mEndereco;
    public String mTelefone;
    public String mEmail;
    public String mEmpresa;
    public TIPO_ENTREGA mTipoEntrega;

    public Usuario() {}

    public Usuario(int id, String nome, String endereco, String telefone, String email, String empresa, TIPO_ENTREGA tipoEntrega) {
        this.mId = id;
        this.mNome = nome;
        this.mEndereco = endereco;
        this.mTelefone = telefone;
        this.mEmail = email;
        this.mEmpresa = empresa;
        this.mTipoEntrega = tipoEntrega;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Usuario)) {
            return false;
        }
        if(obj == this) {
            return true;
        }

        Usuario usuario = (Usuario) obj;
        return this.mNome.equals(usuario.mNome)
                && this.mEndereco.equals(usuario.mEndereco)
                && this.mTelefone.equals(usuario.mTelefone)
                && this.mEmail.equals(usuario.mEmail)
                && this.mEmpresa.equals(usuario.mEmpresa)
                && this.mTipoEntrega.getValue() == usuario.mTipoEntrega.getValue();

    }
}