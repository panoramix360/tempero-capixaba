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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityloop.android.temperocapixaba.database.CardapioLab;
import com.creativityloop.android.temperocapixaba.database.ItemPedidoLab;
import com.creativityloop.android.temperocapixaba.database.PedidoLab;
import com.creativityloop.android.temperocapixaba.model.Cardapio;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;
import com.creativityloop.android.temperocapixaba.recyclerView.ItemPedidoAdapter;
import com.creativityloop.android.temperocapixaba.util.DateUtils;
import com.creativityloop.android.temperocapixaba.util.CardapioUIUpdater;

import java.util.GregorianCalendar;
import java.util.List;

public class CardapioDiarioFragment extends Fragment implements CardapioUIUpdater {

    private Cardapio mCardapio;

    private Pedido mPedido;
    private List<ItemPedido> mItensPedido;

    private GregorianCalendar mToday;

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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mCardapioRecyclerView.setHasFixedSize(true);

        mCardapioRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mFazerPedidoButton = (Button) v.findViewById(R.id.fazer_pedido_button);
        mFazerPedidoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAnyItemPedidoChecked()) {
                    mPedido = new Pedido(null, "", DateUtils.getToday());
                    PedidoLab.get(getActivity()).savePedido(mPedido);

                    for (ItemPedido itemPedido : mItensPedido) {
                        if (itemPedido.isChecked()) {
                            itemPedido.mPedido = mPedido;
                            ItemPedidoLab.get(getActivity()).saveItemPedido(itemPedido);
                        }
                    }
                    Intent intent = ResumoPedidoActivity.newIntent(getActivity(), mPedido.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Selecione pelo menos 1 prato", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mToday = DateUtils.getToday();

        int contadorCardapio = DateUtils.getDayOfWeek(mToday) - 1;

        // load cardapio
        CardapioLab.get(getActivity()).getCardapio(this, contadorCardapio);

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
            case R.id.menu_item_sobre:
                Intent intent = SobreActivity.newIntent(getActivity());
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    public void updateUI(Cardapio cardapio) {
        mCardapio = cardapio;
        initCardapio();

        ItemPedidoAdapter mAdapter = new ItemPedidoAdapter(getActivity(), mItensPedido);
        mCardapioRecyclerView.setAdapter(mAdapter);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(R.string.cardapio_diario_title);
    }

    private void initCardapio() {
        mPedido = PedidoLab.get(getActivity()).getPedido(mToday);

        if(mPedido == null) {
            mItensPedido = ItemPedidoLab.get(getActivity()).createItensPedidoComCardapio(mCardapio);
        } else {
            mItensPedido = ItemPedidoLab.get(getActivity()).getItemPedidos(mPedido.getId());
        }

        mCardapioTitle.setText(getString(R.string.cardapio_diario_format, DateUtils.formatDate(mToday)));
    }

    private boolean isAnyItemPedidoChecked() {
        for(ItemPedido itemPedido : mItensPedido) {
            if(itemPedido.isChecked()) {
                return true;
            }
        }
        return false;
    }
}
