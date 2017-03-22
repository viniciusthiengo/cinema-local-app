package br.com.thiengo.cinemalocalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.calldorado.Calldorado;
import com.calldorado.CalldoradoEventsManager;
import com.squareup.picasso.Picasso;

import br.com.thiengo.cinemalocalapp.data.Mock;
import br.com.thiengo.cinemalocalapp.domain.Filme;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if( getSupportActionBar() != null ){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Filme filme = getIntent().getParcelableExtra(Filme.KEY);

        CollapsingToolbarLayout collapsing = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout) ;
        collapsing.setTitle( filme.getNome() );

        ImageView ivHeader = (ImageView) findViewById(R.id.iv_header);
        Picasso
                .with(this)
                .load( filme.getUrlImagem() )
                .into( ivHeader );

        TextView tvSinopse = (TextView) findViewById(R.id.tv_sinopse);
        tvSinopse.setText( filme.getSinopse() );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
