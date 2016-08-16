package com.creativityloop.android.temperocapixaba.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.activity.FinalizarPedidoActivity;
import com.creativityloop.android.temperocapixaba.database.PedidoLab;
import com.creativityloop.android.temperocapixaba.database.PratoLab;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;

import java.util.ArrayList;
import java.util.List;

public class ResumoPedidoFragment extends Fragment {

    private static final int REQUEST_PEDIDO = 1;
    private static final String ARG_PEDIDO_ID = "pedido_id";
    private static final String ARG_IS_DETALHES = "is_detalhes";

    private long mPedidoId;
    private boolean mIsDetalhes;
    private Pedido mPedido;
    private List<ItemPedido> mItensPedido;

    // UI
    private ListView mResumoPedido;
    private Button mConfirmarPedido;

    public static ResumoPedidoFragment newInstance(long pedidoId, boolean isDetalhes) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PEDIDO_ID, pedidoId);
        args.putSerializable(ARG_IS_DETALHES, isDetalhes);

        ResumoPedidoFragment fragment = new ResumoPedidoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPedidoId = (long) getArguments().getSerializable(ARG_PEDIDO_ID);
        mPedido = PedidoLab.get(getActivity()).getPedido(mPedidoId);
        mItensPedido = new ArrayList<>();

        for(ItemPedido itemPedido : mPedido.getItensPedido()) {
            ItemPedido itemPedidoToCopy = new ItemPedido();
            itemPedidoToCopy.setQuantidadePequena(itemPedido.getQuantidadePequena());
            itemPedidoToCopy.setQuantidadeGrande(itemPedido.getQuantidadeGrande());
            itemPedidoToCopy.setPrato(PratoLab.get(getActivity()).getPrato(itemPedido.getPratoId()));
            mItensPedido.add(itemPedidoToCopy);
        }

        mIsDetalhes = (boolean) getArguments().getSerializable(ARG_IS_DETALHES);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resumo_pedido, container, false);

        mResumoPedido = (ListView) v.findViewById(R.id.resumo_pedido_list_view);
        mResumoPedido.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mItensPedido.toArray(new ItemPedido[mItensPedido.size()])));

        mConfirmarPedido = (Button) v.findViewById(R.id.confirmar_pedido_button);

        if(mIsDetalhes) {
            mConfirmarPedido.setVisibility(View.INVISIBLE);
        } else {
            mConfirmarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = FinalizarPedidoActivity.newIntent(getActivity(), mPedidoId);
                    startActivityForResult(intent, REQUEST_PEDIDO);
                }
            });
        }

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_PEDIDO) {
            if(data == null) {
                return;
            }

            mPedidoId = FinalizarPedidoActivity.getPedidoId(data);
        }
    }
}
