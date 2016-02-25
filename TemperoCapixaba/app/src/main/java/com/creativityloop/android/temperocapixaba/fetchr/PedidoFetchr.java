package com.creativityloop.android.temperocapixaba.fetchr;

import android.net.Uri;
import android.util.Log;

import com.creativityloop.android.temperocapixaba.model.Pedido;
import com.creativityloop.android.temperocapixaba.model.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by LucasReis on 10/02/2016.
 */
public class PedidoFetchr extends Fetchr {
    private static final String TAG = "PedidoFetchr";
    public static final String URL_POST_PEDIDO = "create-pedido?";

    public boolean savePedido(Pedido pedido) {
        boolean isError = false;

        try {
            String data = "nome=" + URLEncoder.encode(pedido.mUsuario.mNome, "UTF-8");
            data += "&endereco=" + URLEncoder.encode(pedido.mUsuario.mEndereco, "UTF-8");
            data += "&telefone=" + URLEncoder.encode(pedido.mUsuario.mTelefone, "UTF-8");
            data += "&data=" + URLEncoder.encode(pedido.mData.toString(), "UTF-8");

            String url = Uri.parse(URL_BASE + URL_POST_PEDIDO + data)
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
}
