package com.sulitous.biti.popularmovies.utilities;

import com.sulitous.biti.popularmovies.Movies;
import com.sulitous.biti.popularmovies.Trailers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OpenMovieJsonUtils {

    public static List<Movies> getSimpleMoviesDetailsFromJson(String s)throws JSONException {

        final String RESULT = "results";
        final String POSTER_IMAGE_PATH = "poster_path";
        final String TITLE = "title";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";
        final String LANGUAGE = "original_language";
        final String BACKDROP_IMAGE_PATH = "backdrop_path";
        final String ADULT = "adult";
        final String VOTE_COUNT = "vote_count";
        final String VOTE_AVERAGE  = "vote_average";
        final String MOVIE_ID = "id";

        JSONObject movieJson = new JSONObject(s);
        JSONArray movieArray = movieJson.getJSONArray(RESULT);

        List<Movies> moviesList = new ArrayList<>();

        for (int i = movieArray.length(); i > 0; i--){

            JSONObject oneMovie = movieArray.getJSONObject(i-1);

            String posterPath =oneMovie.getString(POSTER_IMAGE_PATH);
            String title = oneMovie.getString(TITLE);
            String overview = oneMovie.getString(OVERVIEW);
            String backDropPath = oneMovie.getString(BACKDROP_IMAGE_PATH);
            String releaseDate = oneMovie.getString(RELEASE_DATE);
            String language = oneMovie.getString(LANGUAGE);
            boolean adult = oneMovie.getBoolean(ADULT);
            String voteAverage = oneMovie.getString(VOTE_AVERAGE);
            int voteCount = oneMovie.getInt(VOTE_COUNT);
            int movieID = oneMovie.getInt(MOVIE_ID);

            Movies movies = new Movies();
            movies.setOverview(overview);
            movies.setPosterImage(posterPath);
            movies.setTitle(title);
            movies.setBackDropImage(backDropPath);
            movies.setReleaseDate(releaseDate);
            movies.setLanguage(language);
            movies.setAdult(adult);
            movies.setVoteAverage(voteAverage);
            movies.setVoteCount(voteCount);
            movies.setMovieId(movieID);
            moviesList.add(0,movies);
        }
        return moviesList;
    }

    public static List<Trailers> getSimpleTrailerDetailsFromJson(String s) throws JSONException{

        final String RESULT = "results";
        final String KEY = "key";
        final String NAME = "name";

        JSONObject trailerJson = new JSONObject(s);
        JSONArray trailerArray = trailerJson.getJSONArray(RESULT);

        List<Trailers> trailersList = new ArrayList<>();

        for (int i = trailerArray.length(); i > 0; i--){

            JSONObject oneTrailer = trailerArray.getJSONObject(i-1);

            String name = oneTrailer.getString(NAME);
            String key = oneTrailer.getString(KEY);

            Trailers trailers = new Trailers();
            trailers.setKey(key);
            trailers.setName(name);
            trailersList.add(0,trailers);
        }
        return trailersList;
    }
}
