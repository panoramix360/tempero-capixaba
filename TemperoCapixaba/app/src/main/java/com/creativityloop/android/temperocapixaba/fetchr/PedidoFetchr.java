package com.creativityloop.android.temperocapixaba.fetchr;

import android.net.Uri;
import android.util.Log;

import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;
import com.creativityloop.android.temperocapixaba.model.Usuario;
import com.creativityloop.android.temperocapixaba.util.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LucasReis on 10/02/2016.
 */
public class PedidoFetchr extends Fetchr {
    private static final String TAG = "PedidoFetchr";
    public static final String URL_POST_PEDIDO = "create-pedido?";
    public static final String URL_GET_PEDIDO = "get-pedido/";

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

    public List<Pedido> fetchPedidosByUserId(int userId) {
        List<Pedido> pedidos = new ArrayList<Pedido>();

        try {
            String url = Uri.parse(URL_BASE + URL_GET_PEDIDO + userId)
                    .buildUpon()
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);

            JSONObject jsonBody = new JSONObject(jsonString);

            if(!(boolean) jsonBody.get("vazio")) {
                parsePedidos(pedidos, userId, jsonBody);
            }
        } catch(JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch(IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }

        return pedidos;
    }

    private void parsePedidos(List<Pedido> pedidos, int userId, JSONObject jsonBody) throws JSONException {
        JSONArray pedidosJson = jsonBody.getJSONArray("pedidos");

        for(int i = 0; i < pedidosJson.length(); i++) {
            JSONObject pedidoJson = pedidosJson.getJSONObject(i);
            Usuario usuario = new Usuario();
            usuario.mId = userId;
            Pedido pedido = new Pedido(pedidoJson.getInt("cd_pedido"), usuario, pedidoJson.getString("endereco"), DateUtils.formatDate(DateUtils.getToday()));
            pedido.mItensPedido = new ArrayList<>();
            JSONArray itensPedidoJson = pedidoJson.getJSONArray("itens");
            for(int j = 0; j < itensPedidoJson.length(); j++) {
                JSONObject itemPedidoJson = itensPedidoJson.getJSONObject(j);
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.mPratoId = itemPedidoJson.getInt("cd_prato");
                itemPedido.mQuantidadePequena = itemPedidoJson.getInt("qtd_pequena");
                itemPedido.mQuantidadeGrande = itemPedidoJson.getInt("qtd_grande");
                pedido.mItensPedido.add(itemPedido);
            }
            pedidos.add(pedido);
        }
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
