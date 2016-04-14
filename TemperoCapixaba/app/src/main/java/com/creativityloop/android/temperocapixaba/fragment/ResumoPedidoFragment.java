package com.creativityloop.android.temperocapixaba.fragment;

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
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;

public class ResumoPedidoFragment extends Fragment {

    private static final String ARG_PEDIDO_ID = "pedido_id";
    private static final String ARG_IS_DETALHES = "is_detalhes";

    private long mPedidoId;
    private boolean mIsDetalhes;
    private Pedido mPedido;

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

        mIsDetalhes = (boolean) getArguments().getSerializable(ARG_IS_DETALHES);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resumo_pedido, container, false);

        mResumoPedido = (ListView) v.findViewById(R.id.resumo_pedido_list_view);
        mResumoPedido.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mPedido.getItensPedido().toArray(new ItemPedido[mPedido.getItensPedido().size()])));

        mConfirmarPedido = (Button) v.findViewById(R.id.confirmar_pedido_button);

        if(mIsDetalhes) {
            mConfirmarPedido.setVisibility(View.INVISIBLE);
        } else {
            mConfirmarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = FinalizarPedidoActivity.newIntent(getActivity(), mPedidoId);
                startActivity(intent);
            }
            });
        }

        return v;
    }
}
