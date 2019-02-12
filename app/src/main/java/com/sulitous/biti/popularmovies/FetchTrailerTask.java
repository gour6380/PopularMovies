package com.sulitous.biti.popularmovies;

import android.os.AsyncTask;

import com.sulitous.biti.popularmovies.utilities.NetworkUtils;
import com.sulitous.biti.popularmovies.utilities.OpenMovieJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class FetchTrailerTask extends AsyncTask<Integer,Void,List<Trailers>> {

    private onTaskProcess onTaskProcess;

    FetchTrailerTask(onTaskProcess onTaskProcess) {
        this.onTaskProcess = onTaskProcess;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onTaskProcess.onPre();
    }

    @Override
    protected List<Trailers> doInBackground(Integer... ints) {
        if (ints.length == 0) {
            return null;
        }

        int id =ints[0];
        URL movieRequestUrl = NetworkUtils.buildVideoURL(id);

        try {
            String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

            return OpenMovieJsonUtils.getSimpleTrailerDetailsFromJson(jsonMovieResponse);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Trailers> trailers) {
        onTaskProcess.onCompleted(trailers);
    }

    interface onTaskProcess{
        void onPre();
        void onCompleted(List<Trailers> trailerses);
    }
}
