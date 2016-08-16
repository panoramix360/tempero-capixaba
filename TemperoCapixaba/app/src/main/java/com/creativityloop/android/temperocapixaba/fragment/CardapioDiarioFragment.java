package com.creativityloop.android.temperocapixaba.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.activity.ResumoPedidoActivity;
import com.creativityloop.android.temperocapixaba.activity.SobreActivity;
import com.creativityloop.android.temperocapixaba.database.ItemPedidoLab;
import com.creativityloop.android.temperocapixaba.database.PedidoLab;
import com.creativityloop.android.temperocapixaba.fetchr.CardapioFetchr;
import com.creativityloop.android.temperocapixaba.model.Cardapio;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;
import com.creativityloop.android.temperocapixaba.model.StatusPedido;
import com.creativityloop.android.temperocapixaba.recyclerView.ItemPedidoAdapter;
import com.creativityloop.android.temperocapixaba.util.DateUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.GregorianCalendar;
import java.util.List;

public class CardapioDiarioFragment extends Fragment {

    private static final String KEY_ITENS_PEDIDO = "ItensPedido";

    private Cardapio mCardapio;

    private Pedido mPedido;
    private List<ItemPedido> mItensPedido;

    private GregorianCalendar mToday;

    // UI
    private TextView mDataTextView;
    private RecyclerView mCardapioRecyclerView;
    private Button mFazerPedidoButton;
    private ImageButton mSobreImageButton;

    public static CardapioDiarioFragment newInstance() {
        CardapioDiarioFragment fragment = new CardapioDiarioFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setSavedInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cardapio_diario, container, false);

        mDataTextView = (TextView) v.findViewById(R.id.data_text_view);

        mCardapioRecyclerView = (RecyclerView) v.findViewById(R.id.cardapio_recycler_view);
        mCardapioRecyclerView.setHasFixedSize(true);
        mCardapioRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFazerPedidoButton = (Button) v.findViewById(R.id.fazer_pedido_button);
        mFazerPedidoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerPedido();
            }
        });

        mSobreImageButton = (ImageButton) v.findViewById(R.id.sobre_image_button);
        mSobreImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sobre();
            }
        });

        mToday = DateUtils.getToday();

        int contadorCardapio = DateUtils.getDayOfWeek(mToday) - 1;

        // load cardapio
        new FetchCardapioTask().execute(contadorCardapio);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        savedInstanceState.putString(KEY_ITENS_PEDIDO, gson.toJson(mItensPedido));
    }

    private void setSavedInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            Type listItemPedidoType = new TypeToken<List<ItemPedido>>() {}.getType();

            mItensPedido = gson.fromJson(savedInstanceState.getString(KEY_ITENS_PEDIDO), listItemPedidoType);
        }
    }

    public void updateUI() {
        initCardapio();

        ItemPedidoAdapter mAdapter = new ItemPedidoAdapter(getActivity(), mItensPedido);
        mCardapioRecyclerView.setAdapter(mAdapter);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(R.string.cardapio_diario_title);
    }

    private void initCardapio() {
        mPedido = PedidoLab.get(getActivity()).getPedido(mToday);

        if(mPedido == null && mItensPedido == null) {
            mItensPedido = ItemPedidoLab.get(getActivity()).createItensPedidoComCardapio(mCardapio);
        } else if(mItensPedido == null) {
            mItensPedido = ItemPedidoLab.get(getActivity()).getItemPedidos(mPedido.getId());
        }

        mDataTextView.setText(DateUtils.formatDateWithDescription(mToday));
    }

    private boolean isAnyItemPedidoChecked() {
        for(ItemPedido itemPedido : mItensPedido) {
            if(isItemPedidoValid(itemPedido)) {
                return true;
            }
        }
        return false;
    }

    private void fazerPedido() {
        if (isAnyItemPedidoChecked()) {
            if(mPedido == null) {
                mPedido = new Pedido();
                mPedido.setData(DateUtils.formatDate(DateUtils.getToday()));
                mPedido.setStatusByCodigo(StatusPedido.NAO_ATENDIDO.getValue());
            }
            mPedido.setId(PedidoLab.get(getActivity()).savePedido(mPedido));

            for (ItemPedido itemPedido : mItensPedido) {
                if (isItemPedidoValid(itemPedido)) {
                    ItemPedidoLab.get(getActivity()).saveItemPedidoByPedidoId(mPedido.getId(), itemPedido);
                }
            }
            Intent intent = ResumoPedidoActivity.newIntent(getActivity(), mPedido.getId(), false);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Selecione e preencha pelo menos 1 prato", Toast.LENGTH_SHORT).show();
        }
    }

    private void sobre() {
        Intent intent = SobreActivity.newIntent(getActivity());
        startActivity(intent);
    }

    private boolean isItemPedidoValid(ItemPedido itemPedido) {
        return (itemPedido.getQuantidadePequena() > 0 || itemPedido.getQuantidadeGrande() > 0);
    }

    private class FetchCardapioTask extends AsyncTask<Integer, Void, Cardapio> {
        @Override
        protected Cardapio doInBackground(Integer... params) {
            if(params.length > 0) {
                return new CardapioFetchr().fetchCardapio(params[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Cardapio cardapio) {
            mCardapio = cardapio;
            updateUI();
        }
    }
}
