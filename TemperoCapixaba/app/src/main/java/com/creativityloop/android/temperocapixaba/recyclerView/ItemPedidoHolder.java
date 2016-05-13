package com.creativityloop.android.temperocapixaba.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;

public class ItemPedidoHolder extends RecyclerView.ViewHolder {

    private ItemPedido mItemPedido;

    private Context mContext;

    // UI
    private TextView mNomeTextView;

    private Button mMinusPequenaButton;
    private Button mMaxPequenaButton;
    private TextView mPratoQuantidadePequena;

    private Button mMinusGrandeButton;
    private Button mMaxGrandeButton;
    private TextView mPratoQuantidadeGrande;

    public ItemPedidoHolder(View itemView, Context context) {
        super(itemView);

        mContext = context;

        mNomeTextView = (TextView) itemView.findViewById(R.id.prato_name_text_view);

        mMinusPequenaButton = (Button) itemView.findViewById(R.id.minus_pequena_button);
        mMaxPequenaButton = (Button) itemView.findViewById(R.id.max_pequena_button);
        mPratoQuantidadePequena = (TextView) itemView.findViewById(R.id.prato_quantidade_pequena_text_view);

        mMinusGrandeButton = (Button) itemView.findViewById(R.id.minus_grande_button);
        mMaxGrandeButton = (Button) itemView.findViewById(R.id.max_grande_button);
        mPratoQuantidadeGrande = (TextView) itemView.findViewById(R.id.prato_quantidade_grande_text_view);
    }

    public void bindItemPedido(ItemPedido itemPedido) {
        mItemPedido = itemPedido;
        mNomeTextView.setText(mItemPedido.mPrato.getNome());

        mMinusPequenaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemPedido.mQuantidadePequena = sumPratoQuantidade(-1, mPratoQuantidadePequena);
            }
        });

        mMaxPequenaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemPedido.mQuantidadePequena = sumPratoQuantidade(1, mPratoQuantidadePequena);
            }
        });

        mMinusGrandeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemPedido.mQuantidadeGrande = sumPratoQuantidade(-1, mPratoQuantidadeGrande);
            }
        });

        mMaxGrandeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemPedido.mQuantidadeGrande = sumPratoQuantidade(1, mPratoQuantidadeGrande);
            }
        });
    }

    private int sumPratoQuantidade(int value, TextView textView) {
        int quantidade = Integer.parseInt(textView.getText().toString());
        if(quantidade <= 0 && value < 0) {
            Toast.makeText(mContext, "Quantidade não pode ser menor que zero.", Toast.LENGTH_SHORT).show();
        } else {
            quantidade += value;
            textView.setText(quantidade + "");
        }
        return quantidade;
    }
}
