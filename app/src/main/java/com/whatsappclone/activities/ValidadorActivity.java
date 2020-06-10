package com.whatsappclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.whatsappclone.R;

public class ValidadorActivity extends AppCompatActivity {

    private Button btn_validar;
    private EditText et_cod_validacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        et_cod_validacao    =   findViewById(R.id.et_cod_validacao);
        btn_validar         =   findViewById(R.id.btn_validar);

        SimpleMaskFormatter simpleMaskFormatterCod = new SimpleMaskFormatter("N - N - N - N");

        MaskTextWatcher maskTextWatcherCod = new MaskTextWatcher(et_cod_validacao, simpleMaskFormatterCod);

        et_cod_validacao.addTextChangedListener(maskTextWatcherCod);
    }
}