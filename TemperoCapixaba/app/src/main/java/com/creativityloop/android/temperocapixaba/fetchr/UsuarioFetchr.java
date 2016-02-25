package com.creativityloop.android.temperocapixaba.fetchr;

import android.net.Uri;
import android.util.Log;

import com.creativityloop.android.temperocapixaba.model.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by LucasReis on 10/02/2016.
 */
public class UsuarioFetchr extends Fetchr {

    private static final String TAG = "UsuarioFetchr";
    public static final String URL_POST_USUARIO = "create-user?";

    public boolean saveUsuario(Usuario usuario) {
        boolean isError = false;

        try {
            String data = "nome=" + URLEncoder.encode(usuario.mNome, "UTF-8");
            data += "&endereco=" + URLEncoder.encode(usuario.mEndereco, "UTF-8");
            data += "&telefone=" + URLEncoder.encode(usuario.mTelefone, "UTF-8");
            if(!usuario.mEmail.isEmpty()) {
                data += "&email=" + URLEncoder.encode(usuario.mEmail, "UTF-8");
            }
            if(!usuario.mEmpresa.isEmpty()) {
                data += "&empresa=" + URLEncoder.encode(usuario.mEmpresa, "UTF-8");
            }
            data += "&tipo_entrega=" + URLEncoder.encode(usuario.mTipoEntrega.getValue() + "", "UTF-8");

            String url = Uri.parse(URL_BASE + URL_POST_USUARIO + data)
                    .buildUpon()
                    .build().toString();

            String response = postData(url, data);

            JSONObject jsonResponse = new JSONObject(response);

            isError = (boolean) jsonResponse.get("error");

        } catch (JSONException ex) {
            Log.e(TAG, "Failed to parse JSON", ex);
        }
        catch(IOException ioe) {
            Log.e(TAG, "Failed to save usuario", ioe);
        }

        return !isError;
    }

    public void parseUsuario(Usuario usuario, JSONObject reader) throws JSONException {
        usuario.mNome = (String) reader.get("nome");
        usuario.mEndereco = (String) reader.get("endereco");
        usuario.mTelefone = (String) reader.get("telefone");
        usuario.mEmail = (String) reader.get("email");
        usuario.mEmpresa = (String) reader.get("empresa");
        usuario.mTipoEntrega = Usuario.TIPO_ENTREGA.values()[((int) reader.get("tipo_entrega")) - 1];
    }
}
