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
import com.creativityloop.android.temperocapixaba.model.Mock;
import com.creativityloop.android.temperocapixaba.model.Prato;

import java.util.List;

public class CardapioDiarioFragment extends Fragment {

    private Cardapio mCardapio;

    private TextView mCardapioTitle;
    private RecyclerView mCardapioRecyclerView;
    private Button mFazerPedido;

    private PratoAdapter mAdapter;

    private class PratoHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private Prato mPrato;

        private TextView mNomeTextView;

        public PratoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mNomeTextView = (TextView) itemView.findViewById(R.id.prato_name_text_view);
        }

        public void bindPrato(Prato prato) {
            mPrato = prato;
            mNomeTextView.setText(mPrato.getNome());
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class PratoAdapter extends RecyclerView.Adapter<PratoHolder> {
        private List<Prato> mPratos;

        public PratoAdapter(List<Prato> pratos) {
            mPratos = pratos;
        }

        @Override
        public PratoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_cardapio, parent, false);
            return new PratoHolder(view);
        }

        @Override
        public void onBindViewHolder(PratoHolder holder, int position) {
            Prato prato = mPratos.get(position);
            holder.bindPrato(prato);
        }

        @Override
        public int getItemCount() {
            return mPratos.size();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cardapio_diario, container, false);

        mCardapioTitle = (TextView) v.findViewById(R.id.cardapio_diario_title_text_view);

        mFazerPedido = (Button) v.findViewById(R.id.fazer_pedido_button);
        mFazerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PedidoActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mCardapioRecyclerView = (RecyclerView) v.findViewById(R.id.cardapio_recycler_view);
        mCardapioRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(R.string.cardapio_diario_title);

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

        mAdapter = new PratoAdapter(mCardapio.getPratos());
        mCardapioRecyclerView.setAdapter(mAdapter);
    }

    private void initCardapio() {
        mCardapio = Mock.get(getActivity()).getCardapios().get(0);

        mCardapioTitle.setText(getString(R.string.cardapio_diario_format, Mock.format(mCardapio.getData())));
    }
}
