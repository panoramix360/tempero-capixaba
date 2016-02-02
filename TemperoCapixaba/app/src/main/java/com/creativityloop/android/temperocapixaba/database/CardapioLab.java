package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.model.Cardapio;
import com.creativityloop.android.temperocapixaba.model.Prato;
import com.creativityloop.android.temperocapixaba.util.RestAPI;
import com.creativityloop.android.temperocapixaba.util.RestExecute;
import com.creativityloop.android.temperocapixaba.util.CardapioUIUpdater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LucasReis on 12/01/2016.
 */
public class CardapioLab implements RestExecute {
    private static CardapioLab sCardapioLab;

    private CardapioUIUpdater mUpdater;
    private Cardapio mCardapio;

    public final static String URL_GET_CARDAPIO = "getCardapioAPI/";

    private Context mContext;

    public static CardapioLab get(Context context) {
        if(sCardapioLab == null) {
            sCardapioLab = new CardapioLab(context);
        }

        return sCardapioLab;
    }

    private CardapioLab(Context context) {
        mContext = context.getApplicationContext();
    }

    public void getCardapioAPI(CardapioUIUpdater updater, int contadorCardapio) {
        mUpdater = updater;

        String urlString = URL_GET_CARDAPIO + contadorCardapio;
        mCardapio = new Cardapio();
        mCardapio.setContador(contadorCardapio);

        new RestAPI(this).execute(urlString);
    }

    @Override
    public void execute(String result) throws JSONException {
        JSONObject reader = new JSONObject(result);
        JSONArray jsonArray = reader.getJSONArray("pratos");

        List<Prato> pratos = new ArrayList<Prato>();

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject pratoObj = jsonArray.getJSONObject(i);
            Prato prato = new Prato(pratoObj.getInt("cd_prato"), pratoObj.getString("nome"));
            pratos.add(prato);
        }

        mCardapio.setPratos(pratos);

        mUpdater.updateUI(mCardapio);
    }
}
