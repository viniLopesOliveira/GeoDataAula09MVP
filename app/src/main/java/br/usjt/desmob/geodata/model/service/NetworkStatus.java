package br.usjt.desmob.geodata.model.service;

import android.content.Context;
import android.net.ConnectivityManager;

import br.usjt.desmob.geodata.Contexto;


public class NetworkStatus {

    public static boolean isConnected(){
        Context context = Contexto.getValue();
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
