package com.creativityloop.android.temperocapixaba.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.model.Pedido;

/**
 * Created by LucasReis on 08/04/2016.
 */
public class PedidoDetalhesFragment extends Fragment {

    private static final String ARG_PEDIDO_ID = "pedido_id";

    private Pedido mPedido;

    private ListView mResumoPedido;
    private Button mConfirmarPedido;

    public static PedidoDetalhesFragment newInstance(long pedidoId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PEDIDO_ID, pedidoId);

        PedidoDetalhesFragment fragment = new PedidoDetalhesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pedido_detalhes, container, false);

        return v;
    }

    public void updateUI() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(R.string.pedido_detalhes_subtitle);
    }
}
