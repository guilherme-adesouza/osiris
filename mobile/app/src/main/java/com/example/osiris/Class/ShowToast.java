package com.example.osiris.Class;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ShowToast {
    public static void showToast(Context context, String mensagem, String tipo) {
        try {

            if (tipo.equals("i")) { //info
                Toasty.info(context, mensagem, Toast.LENGTH_SHORT, true).show();
            } else if (tipo.equals("e")) { //erro
                Toasty.error(context, mensagem, Toast.LENGTH_SHORT, true).show();
            } else if (tipo.equals("a")) { //alerta
                Toasty.warning(context, mensagem, Toast.LENGTH_SHORT, true).show();
            } else if (tipo.equals("i")) { //info
                Toasty.info(context, mensagem, Toast.LENGTH_SHORT, true).show();
            } else if (tipo.equals("s")) { //sucesso
                Toasty.success(context, mensagem, Toast.LENGTH_SHORT, true).show();
            }

        } catch (Exception ex) {

        }
    }
}
