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

import java.util.HashMap;

import br.com.thiengo.cinemalocalapp.data.Mock;

public class MainActivity extends AppCompatActivity implements CalldoradoEventsManager.CalldoradoEventCallback {

    private void initializeCalldorado() {
        Calldorado.startCalldorado(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeCalldorado();
        setContentView(R.layout.activity_main);

        initRecycler();

        CalldoradoEventsManager.getInstance().setCalldoradoEventCallback(this);

        Intent intent = new Intent();
        intent.setAction("com.calldorado.android.intent.SET_CUSTOM_ICON");
        intent.setPackage(this.getApplicationContext().getPackageName());
        intent.putExtra("icon","ic_launcher_round");

        String action = getIntent().getAction();
        Uri uri = getIntent().getData();
        //Bundle extras = getIntent().getExtras();
        Log.i("Log", "action: "+action);
        Log.i("Log", "uri: "+uri);
    }

    private void initRecycler(){
        RecyclerView rvFilmes = (RecyclerView) findViewById(R.id.rv_filmes);
        rvFilmes.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvFilmes.setLayoutManager( layoutManager );

        FilmesAdapter adapter = new FilmesAdapter( this, Mock.gerarFilmes() );
        rvFilmes.setAdapter( adapter );
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Calldorado.search(this, new CDOPhoneNumber("+551111"));

        Calldorado.ReEngagementField field = new Calldorado.ReEngagementField(
            "re-app",
            "https://play.google.com/store/apps/details?id=br.thiengocalopsita&hl=pt_BR",
            "Filmes que estão em cartaz" );
        Calldorado.setupDynamicReEngagementField(this, field);


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


}
