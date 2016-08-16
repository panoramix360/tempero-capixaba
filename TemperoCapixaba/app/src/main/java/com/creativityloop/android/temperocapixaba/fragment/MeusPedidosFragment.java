package com.creativityloop.android.temperocapixaba.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.activity.CardapioDiarioActivity;
import com.creativityloop.android.temperocapixaba.database.PedidoLab;
import com.creativityloop.android.temperocapixaba.database.PratoLab;
import com.creativityloop.android.temperocapixaba.database.UsuarioLab;
import com.creativityloop.android.temperocapixaba.fetchr.CardapioFetchr;
import com.creativityloop.android.temperocapixaba.fetchr.PedidoFetchr;
import com.creativityloop.android.temperocapixaba.model.Cardapio;
import com.creativityloop.android.temperocapixaba.model.Pedido;
import com.creativityloop.android.temperocapixaba.model.Prato;
import com.creativityloop.android.temperocapixaba.model.Usuario;
import com.creativityloop.android.temperocapixaba.recyclerView.PedidoAdapter;
import com.creativityloop.android.temperocapixaba.util.DateUtils;

import java.util.List;

public class MeusPedidosFragment extends Fragment {

    private Usuario mUsuario;
    private List<Pedido> mPedidos;

    private TextView mNaoTemPedidos;
    private Button mFazerPedido;
    private LinearLayout mTopoLinearLayout;
    private RecyclerView mPedidosRecyclerView;

    public static MeusPedidosFragment newInstance() {
        MeusPedidosFragment fragment = new MeusPedidosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meus_pedidos, container, false);

        mNaoTemPedidos = (TextView) v.findViewById(R.id.nao_tem_pedidos_text_view);

        mPedidosRecyclerView = (RecyclerView) v.findViewById(R.id.meus_pedidos_recycler_view);
        mPedidosRecyclerView.setHasFixedSize(true);
        mPedidosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTopoLinearLayout = (LinearLayout) v.findViewById(R.id.meus_pedidos_topo_linear_layout);

        mFazerPedido = (Button) v.findViewById(R.id.fazer_pedido_button);
        mFazerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CardapioDiarioActivity.newIntent(getActivity()));
            }
        });

        mUsuario = UsuarioLab.get(getActivity()).getLastUsuario();
        if(mUsuario != null) {
            // load pedidos
            new FetchPedidoTask().execute(mUsuario.getId());
        } else {
            updateUI();
        }

        List<Prato> pratos = PratoLab.get(getActivity()).getPratos();
        if(pratos.size() == 0) {
            int contadorCardapio = DateUtils.getDayOfWeek(DateUtils.getToday()) - 1;
            new FetchCardapioTask().execute(contadorCardapio);
        }

        return v;
    }

    public void updateUI() {
        if(mPedidos != null && mPedidos.size() > 0) {
            PedidoAdapter adapter = new PedidoAdapter(getActivity(), mPedidos);
            mPedidosRecyclerView.setAdapter(adapter);
        } else {
            mTopoLinearLayout.setVisibility(View.GONE);
            mPedidosRecyclerView.setVisibility(View.GONE);
            mNaoTemPedidos.setVisibility(View.VISIBLE);
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(R.string.meus_pedidos_text_view);
    }

    private class FetchPedidoTask extends AsyncTask<Integer, Void, List<Pedido>> {
        @Override
        protected List<Pedido> doInBackground(Integer... params) {
            int userId = params[0];
            return new PedidoFetchr().fetchPedidosByUserId(userId);
        }

        @Override
        protected void onPostExecute(List<Pedido> result) {
            mPedidos = result;
            updateUI();
        }
    }

    private class FetchCardapioTask extends AsyncTask<Integer, Void, Cardapio> {
        @Override
        protected Cardapio doInBackground(Integer... params) {
            if(params.length > 0) {
                return new CardapioFetchr().fetchCardapio(params[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Cardapio cardapio) {
            for(Prato prato : cardapio.getPratos()) {
                PratoLab.get(getActivity()).savePrato(prato);
            }
        }
    }
}
