package com.creativityloop.android.temperocapixaba.model;

import com.orm.SugarRecord;

import java.util.UUID;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Prato extends SugarRecord {

    public String mNome;

    public Prato() {}

    public Prato(String nome) {
        this.mNome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prato prato = (Prato) o;

        return !(mNome != null ? !mNome.equals(prato.mNome) : prato.mNome != null);

    }
}
