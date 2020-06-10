package com.whatsappclone.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permission {

    public static Boolean validaPermissoes(Integer requestCode, Activity activity, String[] permissoes) {

        if ( Build.VERSION.SDK_INT >=23 ){

            List<String> listaPermissoes = new ArrayList<String>();

            for(String permissao : permissoes){
                Boolean validaPermissao = ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_GRANTED;

                if (!validaPermissao){
                    listaPermissoes.add(permissao);
                }
            }

            if (listaPermissoes.isEmpty()) return true;

            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
        }

        return true;
    }

}
