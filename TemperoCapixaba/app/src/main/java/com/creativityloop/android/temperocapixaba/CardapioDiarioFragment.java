package com.creativityloop.android.temperocapixaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.creativityloop.android.temperocapixaba.model.Cardapio;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Mock;
import com.creativityloop.android.temperocapixaba.model.Pedido;
import com.creativityloop.android.temperocapixaba.model.Prato;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CardapioDiarioFragment extends Fragment {

    private Cardapio mCardapio;

    private Pedido mPedido;
    private List<ItemPedido> mItensPedido;

    // UI
    private TextView mCardapioTitle;
    private RecyclerView mCardapioRecyclerView;
    private Button mFazerPedido;
    private PratoAdapter mAdapter;

    private class PratoHolder extends RecyclerView.ViewHolder {
        private Prato mPrato;

        // UI
        private TextView mNomeTextView;
        private CheckBox mPratoCheckBox;
        private EditText mQuantidadeEditText;

        public PratoHolder(View itemView) {
            super(itemView);

            mNomeTextView = (TextView) itemView.findViewById(R.id.prato_name_text_view);
            mPratoCheckBox = (CheckBox) itemView.findViewById(R.id.prato_checkbox);
            mQuantidadeEditText = (EditText) itemView.findViewById(R.id.prato_quantidade_edit_text);
        }

        public void bindPrato(Prato prato) {
            mPrato = prato;
            mNomeTextView.setText(mPrato.mNome);

            mPratoCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.mPrato = mPrato;
                    if (!mItensPedido.contains(itemPedido)) {
                        mItensPedido.add(itemPedido);
                        mQuantidadeEditText.setEnabled(true);
                    } else {
                        mItensPedido.remove(itemPedido);
                        mQuantidadeEditText.setEnabled(false);
                    }
                }
            });

            mQuantidadeEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.mPrato = mPrato;
                    int index = mItensPedido.indexOf(itemPedido);
                    if (index != -1) {
                        ItemPedido currentItemPedido = mItensPedido.get(index);
                        currentItemPedido.mQuantidade = Integer.parseInt(s.toString());
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });
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

        mCardapioRecyclerView = (RecyclerView) v.findViewById(R.id.cardapio_recycler_view);
        mCardapioRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFazerPedido = (Button) v.findViewById(R.id.fazer_pedido_button);
        mFazerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPedido = new Pedido(null, "", new GregorianCalendar());
                mPedido.save();
                for(ItemPedido itemPedido : mItensPedido) {
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

        mAdapter = new PratoAdapter(mCardapio.getPratos());
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
