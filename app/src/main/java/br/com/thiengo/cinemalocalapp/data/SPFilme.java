package br.com.thiengo.cinemalocalapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import br.com.thiengo.cinemalocalapp.domain.Filme;


public class SPFilme {
    private static final String SP_NOME = "pref";

    public static void saveFilmeVisualizado(Context context, String filme){
        SharedPreferences sp = context.getSharedPreferences( SP_NOME, Context.MODE_PRIVATE );
        sp.edit().putBoolean( filme, true ).apply();
    }

    public static boolean hasFilmeParaVisualizar(Context context, List<Filme> filmes){
        SharedPreferences sp = context.getSharedPreferences( SP_NOME, Context.MODE_PRIVATE );

        for( Filme f : filmes ){
            boolean hasFilme = sp.getBoolean( f.getUrlImagem(), false );
            if( !hasFilme ){
                return true;
            }
        }
        return false;
    }

    public static Filme getFilmeMaisAtualNaoVisualizado(Context context, List<Filme> filmes){
        SharedPreferences sp = context.getSharedPreferences( SP_NOME, Context.MODE_PRIVATE );

        for( Filme f : filmes ){
            boolean hasFilme = sp.getBoolean( f.getUrlImagem(), false );
            if( !hasFilme ){
                return f;
            }
        }
        return null;
    }
}
