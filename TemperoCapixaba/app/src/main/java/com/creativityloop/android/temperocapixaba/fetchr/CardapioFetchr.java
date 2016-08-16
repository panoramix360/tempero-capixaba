package com.creativityloop.android.temperocapixaba.fetchr;

import android.net.Uri;
import android.util.Log;

import com.creativityloop.android.temperocapixaba.model.Cardapio;
import com.creativityloop.android.temperocapixaba.model.Prato;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class CardapioFetchr extends Fetchr {

    private static final String TAG = "CardapioFetchr";
    public static final String URL_GET_CARDAPIO = "getCardapio/";

    public Cardapio fetchCardapio(int contadorCardapio) {
        Cardapio cardapio = new Cardapio();

        try {
            String url = Uri.parse(URL_BASE + URL_GET_CARDAPIO + contadorCardapio)
                    .buildUpon()
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);

            JSONObject jsonBody = new JSONObject(jsonString);
            parseCardapio(cardapio, jsonBody);
        } catch(JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch(IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }

        return cardapio;
    }

    public void parseCardapio(Cardapio cardapio, JSONObject reader) throws JSONException {
        JSONArray jsonArray = reader.getJSONArray("pratos");

        List<Prato> pratos = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject pratoObj = jsonArray.getJSONObject(i);
            Prato prato = new Prato();
            prato.setId(pratoObj.getInt("cd_prato"));
            prato.setNome(pratoObj.getString("nome"));
            pratos.add(prato);
        }

        cardapio.setPratos(pratos);
    }
}
