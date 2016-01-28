package com.creativityloop.android.temperocapixaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.creativityloop.android.temperocapixaba.database.ItemPedidoLab;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;

import java.util.List;

public class ResumoPedidoFragment extends Fragment {

    private static final String ARG_PEDIDO_ID = "pedido_id";

    private List<ItemPedido> mItemPedidos;

    // UI
    private ListView mResumoPedido;
    private Button mConfirmarPedido;

    public static ResumoPedidoFragment newInstance(long pedidoId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PEDIDO_ID, pedidoId);

        ResumoPedidoFragment fragment = new ResumoPedidoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long pedidoId = (long) getArguments().getSerializable(ARG_PEDIDO_ID);
        mItemPedidos = ItemPedidoLab.get(getActivity()).getItemPedidos(pedidoId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resumo_pedido, container, false);

        initPratos();

        mResumoPedido = (ListView) v.findViewById(R.id.resumo_pedido_list_view);
        mResumoPedido.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mItemPedidos.toArray(new ItemPedido[mItemPedidos.size()])));

        mConfirmarPedido = (Button) v.findViewById(R.id.confirmar_pedido_button);
        mConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FinalizarPedidoActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        return v;
    }

    public void initPratos() {
        for(ItemPedido itemPedido : mItemPedidos) {
            itemPedido.preencherPrato(getActivity());
        }
    }
}
