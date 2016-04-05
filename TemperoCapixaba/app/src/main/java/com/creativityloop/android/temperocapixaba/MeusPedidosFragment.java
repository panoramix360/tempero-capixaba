package com.creativityloop.android.temperocapixaba;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityloop.android.temperocapixaba.database.PedidoLab;
import com.creativityloop.android.temperocapixaba.database.UsuarioLab;
import com.creativityloop.android.temperocapixaba.fetchr.PedidoFetchr;
import com.creativityloop.android.temperocapixaba.fetchr.UsuarioFetchr;
import com.creativityloop.android.temperocapixaba.model.Pedido;
import com.creativityloop.android.temperocapixaba.model.Usuario;
import com.creativityloop.android.temperocapixaba.recyclerView.PedidoAdapter;

import java.util.List;

public class MeusPedidosFragment extends Fragment {

    private Usuario mUsuario;
    private List<Pedido> mPedidos;

    private TextView mNaoTemPedidos;
    private Button mFazerPedido;
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

        mFazerPedido = (Button) v.findViewById(R.id.fazer_pedido_button);
        mFazerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerPedido();
            }
        });

        mPedidosRecyclerView = (RecyclerView) v.findViewById(R.id.meus_pedidos_recycler_view);
        mPedidosRecyclerView.setHasFixedSize(true);
        mPedidosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mUsuario = UsuarioLab.get(getActivity()).getLastUsuario();

        // load pedidos
        new FetchPedidoTask().execute(mUsuario.mId);

        return v;
    }

    private void fazerPedido() {
        Intent intent = CardapioDiarioActivity.newIntent(getActivity());
        startActivity(intent);
    }

    public void updateUI() {
        if(mPedidos.size() > 0) {
            PedidoAdapter adapter = new PedidoAdapter(getActivity(), mPedidos);
            mPedidosRecyclerView.setAdapter(adapter);
        } else {
            mPedidosRecyclerView.setVisibility(View.INVISIBLE);
            mNaoTemPedidos.setVisibility(View.VISIBLE);
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(R.string.cardapio_diario_title);
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
}
