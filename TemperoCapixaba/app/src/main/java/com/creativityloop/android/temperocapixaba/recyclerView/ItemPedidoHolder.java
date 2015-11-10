package com.creativityloop.android.temperocapixaba.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;

public class ItemPedidoHolder extends RecyclerView.ViewHolder {

    private ItemPedido mItemPedido;

    // UI
    private TextView mNomeTextView;
    private CheckBox mPratoCheckBox;
    private EditText mQuantidadePequenaEditText;
    private EditText mQuantidadeGrandeEditText;

    public ItemPedidoHolder(View itemView) {
        super(itemView);

        mNomeTextView = (TextView) itemView.findViewById(R.id.prato_name_text_view);
        mPratoCheckBox = (CheckBox) itemView.findViewById(R.id.prato_checkbox);
        mQuantidadePequenaEditText = (EditText) itemView.findViewById(R.id.prato_quantidade_pequena_edit_text);
        mQuantidadeGrandeEditText = (EditText) itemView.findViewById(R.id.prato_quantidade_grande_edit_text);
    }

    public void bindItemPedido(ItemPedido itemPedido) {
        mItemPedido = itemPedido;
        mNomeTextView.setText(mItemPedido.mPrato.mNome);

        mPratoCheckBox.setChecked(mItemPedido.ismChecked());
        mPratoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mItemPedido.setmChecked(isChecked);
            }
        });
    }
}
