package com.creativityloop.android.temperocapixaba.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.activity.FinalizarPedidoActivity;
import com.creativityloop.android.temperocapixaba.activity.MeusPedidosActivity;
import com.creativityloop.android.temperocapixaba.database.ItemPedidoLab;
import com.creativityloop.android.temperocapixaba.database.PedidoLab;
import com.creativityloop.android.temperocapixaba.database.UsuarioLab;
import com.creativityloop.android.temperocapixaba.fetchr.PedidoFetchr;
import com.creativityloop.android.temperocapixaba.fetchr.UsuarioFetchr;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;
import com.creativityloop.android.temperocapixaba.model.StatusPedido;
import com.creativityloop.android.temperocapixaba.model.Usuario;

import io.realm.Realm;

public class FinalizarPedidoFragment extends Fragment {

    private static final String ARG_PEDIDO_ID = "pedido_id";

    private Realm realm;

    private Pedido mPedido;
    private Usuario mUsuarioSalvo;

    // UI
    private EditText mNome;
    private EditText mEndereco;
    private EditText mTelefone;
    private EditText mEmpresa;
    private EditText mEmail;
    private RadioGroup mTipoEntrega;
    private Button mFinalizarPedido;

    public static FinalizarPedidoFragment newInstance(long pedidoId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PEDIDO_ID, pedidoId);

        FinalizarPedidoFragment fragment = new FinalizarPedidoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long pedidoId = (long) getArguments().getSerializable(ARG_PEDIDO_ID);
        mPedido = PedidoLab.get(getActivity()).getPedido(pedidoId);

        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_finalizar_pedido, container, false);

        mNome = (EditText) v.findViewById(R.id.nome_edit_text);
        mEndereco = (EditText) v.findViewById(R.id.endereco_edit_text);
        mTelefone = (EditText) v.findViewById(R.id.telefone_edit_text);
        mTelefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        mEmpresa = (EditText) v.findViewById(R.id.empresa_edit_text);
        mEmail = (EditText) v.findViewById(R.id.email_edit_text);
        mTipoEntrega = (RadioGroup) v.findViewById(R.id.tipo_entrega_radio_group);

        mFinalizarPedido = (Button) v.findViewById(R.id.finalizar_pedido_button);
        mFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()) {
                    Usuario usuario = mUsuarioSalvo;
                    if(usuario == null) {
                        usuario = UsuarioLab.get(getActivity()).saveUsuario(createUserFromView());
                    }
                    realm.beginTransaction();
                    mPedido.setUsuario(usuario);
                    mPedido.setEndereco(usuario.getEndereco());
                    realm.commitTransaction();

                    new PostPedidoTask().execute(mPedido.getId());
                }
            }
        });

        updateUI();

        setPositionResult();

        return v;
    }

    private Usuario createUserFromView() {
        Usuario usuario = new Usuario();
        usuario.setNome(mNome.getText().toString());
        usuario.setEndereco(mEndereco.getText().toString());
        usuario.setTelefone(mTelefone.getText().toString());
        usuario.setEmpresa(mEmpresa.getText().toString());
        usuario.setEmail(mEmail.getText().toString());
        int selectedId = mTipoEntrega.getCheckedRadioButtonId();
        RadioButton tipoEntrega = (RadioButton) getActivity().findViewById(selectedId);
        if(tipoEntrega.getText() == getResources().getString(R.string.entrega_radio_button)) {
            usuario.setTipoEntregaCodigo(Usuario.TipoEntrega.ENTREGA_ENDERECO.getValue());
        } else if(tipoEntrega.getText() == getResources().getString(R.string.buscar_radio_button)) {
            usuario.setTipoEntregaCodigo(Usuario.TipoEntrega.BUSCAR_LOCAL.getValue());
        }
        return usuario;
    }

    public void updateUI() {
        setUsuario();
    }

    private void setPositionResult() {
        Intent data = new Intent();
        data.putExtra(FinalizarPedidoActivity.EXTRA_PEDIDO_ID, mPedido.getId());
        getActivity().setResult(Activity.RESULT_OK, data);
    }

    public void setUsuario() {
        mUsuarioSalvo = UsuarioLab.get(getActivity()).getLastUsuario();
        if(mUsuarioSalvo != null) {
            mUsuarioSalvo = mUsuarioSalvo.convertFromRealm();
            mNome.setText(mUsuarioSalvo.getNome());
            mEndereco.setText(mUsuarioSalvo.getEndereco());
            mTelefone.setText(mUsuarioSalvo.getTelefone());
            mEmpresa.setText(mUsuarioSalvo.getEmpresa());
            mEmail.setText(mUsuarioSalvo.getEmail());
            if(mUsuarioSalvo.getTipoEntregaCodigo() > 0) {
                RadioButton radioButton = (RadioButton) mTipoEntrega.getChildAt(mUsuarioSalvo.getTipoEntregaCodigo() - 1);
                radioButton.setChecked(true);
            }
        }
    }

    public Boolean validarCampos() {
        Boolean validado = true;

        if(mNome.getText().toString().equals("")) {
            validado = false;
            Toast.makeText(getActivity(), "O nome é obrigatório!", Toast.LENGTH_SHORT).show();
        } else if(mEndereco.getText().toString().equals("")) {
            validado = false;
            Toast.makeText(getActivity(), "O endereço é obrigatório!", Toast.LENGTH_SHORT).show();
        } else if(mTelefone.getText().toString().equals("")) {
            validado = false;
            Toast.makeText(getActivity(), "O telefone é obrigatório!", Toast.LENGTH_SHORT).show();
        } else if(mTipoEntrega.getCheckedRadioButtonId() < 0) {
            validado = false;
            Toast.makeText(getActivity(), "O tipo de entrega deve ser selecionada.", Toast.LENGTH_SHORT).show();
        }

        return validado;
    }

    private class PostPedidoTask extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... params) {
            Realm realmThread = Realm.getDefaultInstance();

            int pedidoId = params[0];

            Pedido pedido = PedidoLab.get(getActivity(), realmThread).getPedido(pedidoId);
            Usuario usuario = UsuarioLab.get(getActivity(), realmThread).getLastUsuario();

            int pedidoIdExterno = 0;
            int usuarioIdExterno = new UsuarioFetchr().saveUsuario(usuario, mUsuarioSalvo);

            if(usuarioIdExterno > 0) {
                pedidoIdExterno = new PedidoFetchr().savePedido(pedido);

                realmThread.beginTransaction();
                usuario.setIdExterno(usuarioIdExterno);
                realmThread.commitTransaction();

                PedidoLab.get(getActivity(), realmThread).savePedidoIdExterno(pedidoId, pedidoIdExterno);
            }

            Intent intent = MeusPedidosActivity.newIntent(getActivity());
            startActivity(intent);

            return usuarioIdExterno > 0 && pedidoId > 0;
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
    }
}
