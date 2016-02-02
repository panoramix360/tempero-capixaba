package com.creativityloop.android.temperocapixaba;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.creativityloop.android.temperocapixaba.database.PedidoLab;
import com.creativityloop.android.temperocapixaba.model.Pedido;

public class FinalizarPedidoFragment extends Fragment {

    private static final String ARG_PEDIDO_ID = "pedido_id";

    private Pedido mPedido;

    // UI
    private EditText mNome;
    private EditText mEndereco;
    private EditText mTelefone;
    private EditText mEmpresa;
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

        updateUI();

        mNome = (EditText) v.findViewById(R.id.nome_edit_text);
        mEndereco = (EditText) v.findViewById(R.id.endereco_edit_text);
        mTelefone = (EditText) v.findViewById(R.id.telefone_edit_text);
        mTelefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        mEmpresa = (EditText) v.findViewById(R.id.empresa_edit_text);
        mTipoEntrega = (RadioGroup) v.findViewById(R.id.tipo_entrega_radio_group);

        mFinalizarPedido = (Button) v.findViewById(R.id.finalizar_pedido_button);
        mFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()) {

                }
            }
        });

        return v;
    }

    public void updateUI() {

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
}