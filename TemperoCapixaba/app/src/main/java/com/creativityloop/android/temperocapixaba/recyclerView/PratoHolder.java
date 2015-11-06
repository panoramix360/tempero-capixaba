package com.creativityloop.android.temperocapixaba.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Prato;

import java.util.List;

/**
 * Created by Guilherme on 05/11/2015.
 */
public class PratoHolder extends RecyclerView.ViewHolder {
    private Prato mPrato;

    // UI
    private TextView mNomeTextView;
    private CheckBox mPratoCheckBox;
    private EditText mQuantidadeEditText;
    private List<ItemPedido> mItensPedido;

    public PratoHolder(View itemView, List<ItemPedido> itensPedido) {
        super(itemView);

        mNomeTextView = (TextView) itemView.findViewById(R.id.prato_name_text_view);
        mPratoCheckBox = (CheckBox) itemView.findViewById(R.id.prato_checkbox);
        mQuantidadeEditText = (EditText) itemView.findViewById(R.id.prato_quantidade_edit_text);

        mItensPedido = itensPedido;
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
