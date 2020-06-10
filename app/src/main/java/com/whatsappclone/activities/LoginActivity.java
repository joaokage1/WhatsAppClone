package com.whatsappclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.whatsappclone.R;
import com.whatsappclone.helper.Preferences;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private Button btn_cadastrar;
    private EditText et_telefone, et_pais, et_ddd, et_nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_cadastrar   = findViewById(R.id.btn_cadastrar);
        et_telefone     = findViewById(R.id.et_telefone);
        et_pais         = findViewById(R.id.et_pais);
        et_ddd          = findViewById(R.id.et_ddd);
        et_nome         = findViewById(R.id.et_nome);

        SimpleMaskFormatter simpleMaskFormatterTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        SimpleMaskFormatter simpleMaskFormatterPais     = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskFormatterDDD      = new SimpleMaskFormatter(" NN ");

        MaskTextWatcher maskTextWatcherTelefone = new MaskTextWatcher(et_telefone, simpleMaskFormatterTelefone);
        MaskTextWatcher maskTextWatcherPais     = new MaskTextWatcher(et_pais, simpleMaskFormatterPais);
        MaskTextWatcher maskTextWatcherDDD      = new MaskTextWatcher(et_ddd, simpleMaskFormatterDDD);

        et_telefone.addTextChangedListener(maskTextWatcherTelefone);
        et_pais.addTextChangedListener(maskTextWatcherPais);
        et_ddd.addTextChangedListener(maskTextWatcherDDD);

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeUsuario = et_nome.getText().toString();
                String telefoneCompleto =
                        et_pais.getText().toString() +
                        et_ddd.getText().toString() +
                        et_telefone.getText().toString();

                String telefoneSemFormatacao = telefoneCompleto.replace("+", "");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-", "");

                //Gerar Token
                Random randomico = new Random();
                Integer numeroRand = randomico.nextInt((9999 - 1000) + 1000);
                String token = String.valueOf(numeroRand);

                //Salvar dados para valdiação
                Preferences preferences = new Preferences(getApplicationContext());
                preferences.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao,token);

                //Envia SMS
                Boolean enviado = enviaSMS("+" + telefoneSemFormatacao, "WhatsApp Codigo de Confirmação "+ token);

                Intent validarActivity = new Intent(getApplicationContext(),ValidadorActivity.class);
                startActivity(validarActivity);
            }
        });
    }

    private  Boolean enviaSMS(String telefone, String msg){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone,null,msg, null,null);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}