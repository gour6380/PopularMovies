package com.sulitous.biti.popularmovies;

import android.os.AsyncTask;
import android.view.View;

import com.sulitous.biti.popularmovies.utilities.NetworkUtils;
import com.sulitous.biti.popularmovies.utilities.OpenMovieJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

class FetchMoviesTask extends AsyncTask<String,Void,List<Movies>> {

    private onTaskProcess onTaskProcess;

    FetchMoviesTask(FetchMoviesTask.onTaskProcess onTaskProcess) {
        this.onTaskProcess = onTaskProcess;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onTaskProcess.onPre();
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
        onTaskProcess.onCompleted(movies);
    }

    interface onTaskProcess{
        void onPre();
        void onCompleted(List<Movies> movies);
    }
}
