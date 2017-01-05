package com.sulitous.biti.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sulitous.biti.popularmovies.utilities.NetworkUtils;
import com.sulitous.biti.popularmovies.utilities.OpenMovieJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler{

    RecyclerView mRecycle;
    MoviesAdapter mAdapter;
    private TextView mErrorDisplay;
    private ProgressBar mProgress;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBar = getSupportActionBar();

        mRecycle = (RecyclerView) findViewById(R.id.recyclerview_movies);
        mErrorDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mProgress = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mAdapter = new MoviesAdapter(this, this);

        GridLayoutManager manager = new GridLayoutManager(this,2);
        mRecycle.setLayoutManager(manager);
        mRecycle.setHasFixedSize(true);
        mRecycle.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String order = sharedPreferences.getString("TYPE","popular");
        if (order.equals("popular")){
            mActionBar.setTitle("Popular Movies");
        }else {
            mActionBar.setTitle("Top Rated Movies");
        }
        loadMoviesData(order);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (id){
            case R.id.popular:
                editor.putString("TYPE","popular");
                mActionBar.setTitle("Popular Movies");
                loadMoviesData("popular");
                editor.apply();
                return true;
            case R.id.top_rated:
                editor.putString("TYPE","top_rated");
                mActionBar.setTitle("Top Rated Movies");
                loadMoviesData("top_rated");
                editor.apply();
                return true;
            default:
                return false;

        }
    }

    private void loadMoviesData(String type){
        showMovieDataView();
        new FetchMoviesTask().execute(type);
    }

    private void showMovieDataView(){
        mErrorDisplay.setVisibility(View.INVISIBLE);
        mRecycle.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecycle.setVisibility(View.INVISIBLE);
        mErrorDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movies movies) {
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        intent.putExtra("URL",movies.getPosterImage());
        intent.putExtra("TITLE",movies.getTitle());
        intent.putExtra("OVERVIEW",movies.getOverview());
        intent.putExtra("LANGUAGE",movies.getLanguage());
        intent.putExtra("RELEASE",movies.getReleaseDate());
        intent.putExtra("ADULT",movies.isAdult());
        intent.putExtra("VOTEAVERAGE",movies.getVoteAverage());
        intent.putExtra("BACKDROP",movies.getBackDropImage());
        intent.putExtra("VOTECOUNT",movies.getVoteCount());
        startActivity(intent);
    }

    public class FetchMoviesTask extends AsyncTask<String,Void,List<Movies>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movies> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            String query =strings[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(query);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                return OpenMovieJsonUtils.getSimpleMoviesDetailsFromJson(jsonMovieResponse);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movies> movies) {
            mProgress.setVisibility(View.INVISIBLE);
            if (movies != null){
                mAdapter.setMoviesData(movies);
            }else {
                showErrorMessage();
            }
        }
    }
}
