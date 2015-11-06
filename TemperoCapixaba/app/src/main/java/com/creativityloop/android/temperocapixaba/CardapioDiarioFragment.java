package com.creativityloop.android.temperocapixaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.creativityloop.android.temperocapixaba.model.Cardapio;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Mock;
import com.creativityloop.android.temperocapixaba.model.Pedido;
import com.creativityloop.android.temperocapixaba.recyclerView.PratoAdapter;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class CardapioDiarioFragment extends Fragment {

    private Cardapio mCardapio;

    private Pedido mPedido;
    private List<ItemPedido> mItensPedido;

    // UI
    private TextView mCardapioTitle;
    private RecyclerView mCardapioRecyclerView;
    private Button mFazerPedidoButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cardapio_diario, container, false);

        mCardapioTitle = (TextView) v.findViewById(R.id.cardapio_diario_title_text_view);

        mCardapioRecyclerView = (RecyclerView) v.findViewById(R.id.cardapio_recycler_view);
        mCardapioRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFazerPedidoButton = (Button) v.findViewById(R.id.fazer_pedido_button);
        mFazerPedidoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPedido = new Pedido(null, "", new GregorianCalendar());
                mPedido.save();
                for (ItemPedido itemPedido : mItensPedido) {
                    itemPedido.mPedido = mPedido;
                    itemPedido.save();
                }
                Intent intent = PedidoActivity.newIntent(getActivity(), mPedido.getId());
                startActivity(intent);
            }
        });

        mItensPedido = new ArrayList<>();

        updateUI();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    private void updateUI() {
        initCardapio();

        PratoAdapter mAdapter = new PratoAdapter(mCardapio.getPratos(), mItensPedido, getActivity());
        mCardapioRecyclerView.setAdapter(mAdapter);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(R.string.cardapio_diario_title);
    }

    private void initCardapio() {
        // TODO trocar por chamada ao banco
        mCardapio = Mock.get(getActivity()).getCardapios().get(0);

        mCardapioTitle.setText(getString(R.string.cardapio_diario_format, Mock.format(mCardapio.getData())));
    }
}
