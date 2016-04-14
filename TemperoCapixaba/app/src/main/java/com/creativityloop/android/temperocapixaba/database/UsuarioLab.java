package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.creativityloop.android.temperocapixaba.model.Cardapio;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Prato;
import com.creativityloop.android.temperocapixaba.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioLab {
    private static UsuarioLab sUsuarioLab;

    private Context mContext;

    public static UsuarioLab get(Context context) {
        if(sUsuarioLab == null) {
            sUsuarioLab = new UsuarioLab(context);
        }

        return sUsuarioLab;
    }

    private UsuarioLab(Context context) {
        mContext = context.getApplicationContext();
    }

    public void saveUsuario(Usuario usuario) {
        Usuario.deleteAll(Usuario.class);
        Usuario.save(usuario);
    }

    public Usuario getLastUsuario() {
        try {
            return Usuario.last(Usuario.class);
        } catch(SQLiteException ex) {
            return null;
        }
    }
}
