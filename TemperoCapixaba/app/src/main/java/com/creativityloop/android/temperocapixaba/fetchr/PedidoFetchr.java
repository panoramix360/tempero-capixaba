package com.creativityloop.android.temperocapixaba.fetchr;

import android.net.Uri;
import android.util.Log;

import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;

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

    public int savePedido(Pedido pedido) {
        int pedidoId = 0;

        try {
            String data = createDataFromPedido(pedido);

            String url = Uri.parse(URL_BASE + URL_POST_PEDIDO + data)
                    .buildUpon()
                    .build().toString();

            String response = postData(url, data);

            JSONObject jsonResponse = new JSONObject(response);

            if(!((boolean) jsonResponse.get("error"))) {
                pedidoId = Integer.parseInt(jsonResponse.get("cd_pedido").toString());
            }

        } catch (JSONException ex) {
            Log.e(TAG, "Failed to parse JSON", ex);
        }
        catch(IOException ioe) {
            Log.e(TAG, "Failed to save usuario", ioe);
        }

        return pedidoId;
    }

    public String createDataFromPedido(Pedido pedido) throws IOException, JSONException {
        String data = "nome=" + URLEncoder.encode(pedido.mUsuario.mNome, "UTF-8");
        data += "&endereco=" + URLEncoder.encode(pedido.mUsuario.mEndereco, "UTF-8");
        data += "&telefone=" + URLEncoder.encode(pedido.mUsuario.mTelefone, "UTF-8");
        data += "&data=" + URLEncoder.encode(pedido.mData, "UTF-8");
        int i = 0;
        for(ItemPedido itemPedido : pedido.mItensPedido) {
            JSONObject itemObject = new JSONObject();
            itemObject.put("cd_prato", itemPedido.mPratoId);
            itemObject.put("qtd_pequena", itemPedido.mQuantidadePequena);
            itemObject.put("qtd_grande", itemPedido.mQuantidadeGrande);
            data += "&itens[" + i + "]=" + itemObject.toString();
            i++;
        }

        return data;
    }
}
