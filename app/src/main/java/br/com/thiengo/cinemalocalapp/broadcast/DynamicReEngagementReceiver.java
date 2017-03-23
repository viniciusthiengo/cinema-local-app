package br.com.thiengo.cinemalocalapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.calldorado.Calldorado;

import java.util.List;

import br.com.thiengo.cinemalocalapp.data.Mock;
import br.com.thiengo.cinemalocalapp.data.SPFilme;
import br.com.thiengo.cinemalocalapp.domain.Filme;


public class DynamicReEngagementReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if( intent.getAction().equalsIgnoreCase("com.calldorado.android.intent.DYNAMIC_RE_ENGAGEMENT_SHOWN") ){

            List<Filme> filmes = Mock.gerarFilmes();
            String fieldName = intent.getExtras().getString("reEngagementName");
            String path = null;
            String fieldLabel = null;

            if( SPFilme.hasFilmeParaVisualizar( context, filmes ) ){
                Filme filme = SPFilme.getFilmeMaisAtualNaoVisualizado( context, filmes );
                path = "android-app://br.com.thiengo.cinemalocalapp/" + filme.getUrlImagem();
                fieldLabel = "Sinopse " + filme.getNome();
            }

            Calldorado.ReEngagementField field = new Calldorado.ReEngagementField(
                    fieldName,
                    path,
                    fieldLabel );

            Calldorado.setupDynamicReEngagementField( context, field );
        }
    }
}



