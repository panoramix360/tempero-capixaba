package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.model.Usuario;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class UsuarioLab {
    private static UsuarioLab sUsuarioLab;
    private static UsuarioLab sUsuarioLabAsync;

    private Context mContext;
    private Realm realm;

    public static UsuarioLab get(Context context) {
        if(sUsuarioLab == null) {
            sUsuarioLab = new UsuarioLab(context);
        }

        return sUsuarioLab;
    }

    public static UsuarioLab get(Context context, Realm realm) {
        if(sUsuarioLabAsync == null) {
            sUsuarioLabAsync = new UsuarioLab(context, realm);
        } else {
            sUsuarioLabAsync.realm = realm;
        }

        return sUsuarioLabAsync;
    }

    private UsuarioLab(Context context) {
        mContext = context.getApplicationContext();
        realm = Realm.getDefaultInstance();
    }

    private UsuarioLab(Context context, Realm realm) {
        mContext = context.getApplicationContext();
        if(realm == null) {
            realm = Realm.getDefaultInstance();
        } else {
            this.realm = realm;
        }
    }

    public Usuario saveUsuario(final Usuario usuario) {
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
        usuarioToInsert.setTipoEntregaCodigo(usuario.getTipoEntregaCodigo());
        realm.commitTransaction();

        return usuarioToInsert;
    }

    public Usuario getLastUsuario() {
        RealmResults<Usuario> usuarios = realm.where(Usuario.class).findAllSorted("mId", Sort.DESCENDING);
        if(usuarios.size() > 0) {
            return usuarios.get(0);
        }
        return null;
    }
}
