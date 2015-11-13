package com.creativityloop.android.temperocapixaba.model;

import com.orm.SugarRecord;

import java.util.UUID;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Prato {

    public int mId;
    public String mNome;

    public Prato(int id, String nome) {
        this.mId = id;
        this.mNome = nome;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prato prato = (Prato) o;

        return !(mNome != null ? !mNome.equals(prato.mNome) : prato.mNome != null);

    }
}
