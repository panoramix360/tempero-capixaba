package com.creativityloop.android.temperocapixaba.fragment;

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
import com.creativityloop.android.temperocapixaba.activity.MeusPedidosActivity;
import com.creativityloop.android.temperocapixaba.database.PedidoLab;
import com.creativityloop.android.temperocapixaba.database.UsuarioLab;
import com.creativityloop.android.temperocapixaba.fetchr.PedidoFetchr;
import com.creativityloop.android.temperocapixaba.fetchr.UsuarioFetchr;
import com.creativityloop.android.temperocapixaba.model.Pedido;
import com.creativityloop.android.temperocapixaba.model.Usuario;

public class FinalizarPedidoFragment extends Fragment {

    private static final String ARG_PEDIDO_ID = "pedido_id";

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
                    Usuario usuario = createUserFromView();
                    mPedido.mUsuario = usuario;
                    mPedido.mEndereco = usuario.mEndereco;
                    new PostPedidoTask().execute(mPedido);
                }
            }
        });

        updateUI();

        return v;
    }

    private Usuario createUserFromView() {
        Usuario usuario = new Usuario();
        usuario.mNome = mNome.getText().toString();
        usuario.mEndereco = mEndereco.getText().toString();
        usuario.mTelefone = mTelefone.getText().toString();
        usuario.mEmpresa = mEmpresa.getText().toString();
        usuario.mEmail = mEmail.getText().toString();
        usuario.mTipoEntrega = Usuario.TIPO_ENTREGA.values()[mTipoEntrega.indexOfChild(getActivity().findViewById(mTipoEntrega.getCheckedRadioButtonId()))];
        return usuario;
    }

    public void updateUI() {
        setUsuario();
    }

    public void setUsuario() {
        mUsuarioSalvo = UsuarioLab.get(getActivity()).getLastUsuario();
        if(mUsuarioSalvo != null) {
            mNome.setText(mUsuarioSalvo.mNome);
            mEndereco.setText(mUsuarioSalvo.mEndereco);
            mTelefone.setText(mUsuarioSalvo.mTelefone);
            mEmpresa.setText(mUsuarioSalvo.mEmpresa);
            mEmail.setText(mUsuarioSalvo.mEmail);
            RadioButton radioButton = (RadioButton) mTipoEntrega.getChildAt(mUsuarioSalvo.mTipoEntrega.getValue() - 1);
            radioButton.setChecked(true);
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

    private class PostPedidoTask extends AsyncTask<Pedido, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Pedido... params) {
            Pedido pedido = params[0];

            int pedidoId = 0;
            int usuarioId = new UsuarioFetchr().saveUsuario(pedido.mUsuario, mUsuarioSalvo);

            if(usuarioId > 0) {
                pedido.mUsuario.mId = usuarioId;
                UsuarioLab.get(getActivity()).saveUsuario(pedido.mUsuario);

                pedidoId = new PedidoFetchr().savePedido(pedido);
                pedido.mId = pedidoId;
                pedido.save();
            }

            Intent intent = MeusPedidosActivity.newIntent(getActivity());
            startActivity(intent);

            return usuarioId > 0 && pedidoId > 0;
        }

        @Override
        protected void onPostExecute(Boolean result) {

        }
    }
}
