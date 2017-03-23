package br.com.thiengo.cinemalocalapp;

import com.calldorado.Calldorado;
import com.calldorado.CalldoradoEventsManager;
import com.calldorado.manual_search.CDOPhoneNumber;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.thiengo.cinemalocalapp.data.Mock;
import br.com.thiengo.cinemalocalapp.data.SPFilme;
import br.com.thiengo.cinemalocalapp.domain.Filme;

public class MainActivity extends AppCompatActivity implements CalldoradoEventsManager.CalldoradoEventCallback {

    private ArrayList<Filme> filmes;

    private void initializeCalldorado() {
        Calldorado.startCalldorado(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeCalldorado();
        setContentView(R.layout.activity_main);

        initRecycler();

        Uri data = getIntent().getData();
        if( data != null ){

            // REENGAJAMENTO DE BOTÃO DINÂMICO
            String path = data.getPath();
            path = path.substring(1, path.length()); /* REMOVENDO BARRA INICIAL */
            Filme filme = getFilmeByUrl( path );

            if( filme != null ){

                Intent intent = new Intent(this, DetalhesActivity.class);
                intent.putExtra( Filme.KEY, filme );
                startActivity( intent );
            }

            // REENGAJAMENTO DE BOTÃO ESTÁTICO
            //if( host.equalsIgnoreCase( getPackageName() )
                    //&& path.equalsIgnoreCase("/lancamento") ){

                /*
                * NOTE QUE O ÚLTIMO FILME ADICIONADO É O PRIMEIRO
                * DA LISTA DE FILMES, ENTÃO O ACESSO A ELE É VIA
                * POSIÇÃO 0.
                * */
                //Intent intent = new Intent(this, DetalhesActivity.class);
                //intent.putExtra( Filme.KEY, Mock.gerarFilmes().get( 0 ) );
                //startActivity( intent );
            //}
        }

        // LISTENER DE CARREGAMENTO DO SDK CALLDORADO
        CalldoradoEventsManager.getInstance().setCalldoradoEventCallback(this);
    }

    private void initRecycler(){
        filmes = Mock.gerarFilmes();

        RecyclerView rvFilmes = (RecyclerView) findViewById(R.id.rv_filmes);
        rvFilmes.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvFilmes.setLayoutManager( layoutManager );

        FilmesAdapter adapter = new FilmesAdapter( this, filmes );
        rvFilmes.setAdapter( adapter );
    }

    @Override
    protected void onResume() {
        super.onResume();

        String path = null;
        String fieldLabel = null;

        if( SPFilme.hasFilmeParaVisualizar( this, filmes ) ){
            Filme filme = SPFilme.getFilmeMaisAtualNaoVisualizado( this, filmes );
            path = "android-app://br.com.thiengo.cinemalocalapp/" + filme.getUrlImagem();
            fieldLabel = "Sinopse " + filme.getNome();
        }

        Calldorado.ReEngagementField field = new Calldorado.ReEngagementField(
                "re-app-filme-nao-visualizado",
                path,
                fieldLabel );

        Calldorado.setupDynamicReEngagementField(this, field);


        // SEARCH DATA CALLDORADO API
        //Calldorado.search(this, new CDOPhoneNumber("+551111"));


        // TARGETING CALLDORADO API
        /*HashMap<Calldorado.TargetingOption, String> map = new HashMap<>();
        map.put(Calldorado.TargetingOption.BirthDate, "2000-08-03" );
        map.put(Calldorado.TargetingOption.Gender, "male" );
        map.put(Calldorado.TargetingOption.Education, "high" );
        map.put(Calldorado.TargetingOption.MaritalStatus, "single" );
        map.put(Calldorado.TargetingOption.HouseholdIncome, "5000" );
        map.put(Calldorado.TargetingOption.ParentalStatus, "childless" );
        map.put(Calldorado.TargetingOption.Interests, "soccer,games" );

        Calldorado.setTargetingOptions(
            this,
            map);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.calldorado_settings){
            Calldorado.createCalloradoSettingsActivity(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadingStarted() {
        Log.i("Log", "onLoadingStarted()");
    }

    @Override
    public void onLoadingFinished() {
        Log.i("Log", "onLoadingFinished()");
    }

    @Override
    public void onLoadingError(String s) {
        Log.i("Log", "onLoadingError(): "+s);
    }

    private Filme getFilmeByUrl( String url ){

        for( Filme f : filmes ){

            if( f.getUrlImagem().equalsIgnoreCase( url ) ){
                return f;
            }
        }
        return null;
    }
}
