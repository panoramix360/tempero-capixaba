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
    public static final String URL_INSERT_USUARIO = "create-user?";
    public static final String URL_UPDATE_USUARIO = "update-user?";

    public int saveUsuario(Usuario usuario, Usuario usuarioAlreadyCreated) {
        int usuarioId = 0;

        try {
            String data = createDataFromUsuario(usuario, (usuarioAlreadyCreated != null) ? usuarioAlreadyCreated.getIdExterno(): 0);

            String response;

            if(usuarioAlreadyCreated == null) {
                response = insertUsuario(data);
            } else if(!usuario.equals(usuarioAlreadyCreated)) {
                response = updateUsuario(data);
            } else {
                return usuarioAlreadyCreated.getIdExterno();
            }

            JSONObject jsonResponse = new JSONObject(response);
            if(!((boolean) jsonResponse.get("error"))) {
                usuarioId = Integer.parseInt(jsonResponse.get("cd_usuario").toString());
            }

        } catch (JSONException ex) {
            Log.e(TAG, "Failed to parse JSON", ex);
        }
        catch(IOException ioe) {
            Log.e(TAG, "Failed to save usuario", ioe);
        }

        return usuarioId;
    }

    public String insertUsuario(String data) throws IOException {
        String url = Uri.parse(URL_BASE + URL_INSERT_USUARIO + data)
                .buildUpon()
                .build().toString();

        return postData(url, data);
    }

    public String updateUsuario(String data) throws IOException {
        String url = Uri.parse(URL_BASE + URL_UPDATE_USUARIO + data)
                .buildUpon()
                .build().toString();

        return postData(url, data);
    }

    public String createDataFromUsuario(Usuario usuario, int idUsuario) throws IOException {
        String data = "nome=" + URLEncoder.encode(usuario.getNome(), "UTF-8");
        data += "&endereco=" + URLEncoder.encode(usuario.getEndereco(), "UTF-8");
        data += "&telefone=" + URLEncoder.encode(usuario.getTelefone(), "UTF-8");
        if(!usuario.getEmail().isEmpty()) {
            data += "&email=" + URLEncoder.encode(usuario.getEmail(), "UTF-8");
        }
        if(!usuario.getEmpresa().isEmpty()) {
            data += "&empresa=" + URLEncoder.encode(usuario.getEmpresa(), "UTF-8");
        }
        data += "&tipo_entrega=" + URLEncoder.encode(usuario.getTipoEntregaCodigo() + "", "UTF-8");
        data += "&cd_usuario=" + URLEncoder.encode(idUsuario + "", "UTF-8");

        return data;
    }

    public void parseUsuario(Usuario usuario, JSONObject reader) throws JSONException {
        usuario.setNome((String) reader.get("nome"));
        usuario.setEndereco((String) reader.get("endereco"));
        usuario.setTelefone((String) reader.get("telefone"));
        usuario.setEmail((String) reader.get("email"));
        usuario.setEmpresa((String) reader.get("empresa"));
        usuario.setTipoEntrega(Usuario.TipoEntrega.values()[((int) reader.get("tipo_entrega")) - 1]);
    }
}
