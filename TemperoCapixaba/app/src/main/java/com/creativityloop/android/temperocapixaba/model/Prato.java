package com.creativityloop.android.temperocapixaba.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Prato {

    private int mId;
    private String mNome;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prato prato = (Prato) o;

        return !(mNome != null ? !mNome.equals(prato.mNome) : prato.mNome != null);
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
}
