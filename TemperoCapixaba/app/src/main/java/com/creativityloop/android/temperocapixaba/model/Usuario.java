package com.creativityloop.android.temperocapixaba.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Usuario extends RealmObject {

    public enum TipoEntrega {
        ENTREGA_ENDERECO(1), BUSCAR_LOCAL(2);

        private final int value;
        TipoEntrega(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    @PrimaryKey
    private int mId;
    private String mNome;
    private String mEndereco;
    private String mTelefone;
    private String mEmail;
    private String mEmpresa;
    private int mTipoEntregaCodigo;

    @Ignore
    private TipoEntrega mTipoEntrega;

    public void setTipoEntregaByCodigo(int tipoEntrega) {
        this.mTipoEntregaCodigo = tipoEntrega;
        this.mTipoEntrega = TipoEntrega.values()[tipoEntrega];

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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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

    public String getTelefone() {
        return mTelefone;
    }

    public void setTelefone(String telefone) {
        mTelefone = telefone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getEmpresa() {
        return mEmpresa;
    }

    public void setEmpresa(String empresa) {
        mEmpresa = empresa;
    }

    public int getTipoEntregaCodigo() {
        return mTipoEntregaCodigo;
    }

    public void setTipoEntregaCodigo(int tipoEntregaCodigo) {
        mTipoEntregaCodigo = tipoEntregaCodigo;
    }

    public TipoEntrega getTipoEntrega() {
        return mTipoEntrega;
    }

    public void setTipoEntrega(TipoEntrega tipoEntrega) {
        mTipoEntrega = tipoEntrega;
    }
}