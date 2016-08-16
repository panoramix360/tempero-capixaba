package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.model.Usuario;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class UsuarioLab {
    private static UsuarioLab sUsuarioLab;

    private Context mContext;
    private Realm realm;

    public static UsuarioLab get(Context context) {
        if(sUsuarioLab == null) {
            sUsuarioLab = new UsuarioLab(context);
        }

        return sUsuarioLab;
    }

    private UsuarioLab(Context context) {
        mContext = context.getApplicationContext();
        realm = Realm.getDefaultInstance();
    }

    public void saveUsuario(final Usuario usuario) {
        realm.beginTransaction();
        realm.where(Usuario.class).findAll().deleteAllFromRealm();
        int nextInt = realm.where(Usuario.class).findAll().size() + 1;
        Usuario usuarioToInsert = realm.createObject(Usuario.class);
        usuarioToInsert.setId(nextInt);
        usuarioToInsert.setNome(usuario.getNome());
        usuarioToInsert.setEndereco(usuario.getEndereco());
        usuarioToInsert.setTelefone(usuario.getTelefone());
        usuarioToInsert.setEmail(usuario.getEmail());
        usuarioToInsert.setEmpresa(usuario.getEmpresa());
        usuarioToInsert.setTipoEntregaByCodigo(usuario.getTipoEntregaCodigo());
        realm.commitTransaction();
    }

    public Usuario getLastUsuario() {
        RealmResults<Usuario> usuarios = realm.where(Usuario.class).findAllSorted("mId", Sort.DESCENDING);
        if(usuarios.size() > 0) {
            return usuarios.get(0);
        }
        return null;
    }
}
