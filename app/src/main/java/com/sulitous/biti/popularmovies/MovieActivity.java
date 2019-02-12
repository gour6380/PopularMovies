package com.sulitous.biti.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler,
        FetchTrailerTask.onTaskProcess{

    private ImageView mImagePosterView,mImageBackDropView;
    private TextView mTitleView,mReleaseView,mTotalView,mLanguageView,mAdultView,mOverviewView;
    private RatingBar mRatingBarView;
    private RecyclerView mTrailerView,mReviewsView;
    private TrailerAdapter mTrailerAdapter;
    private ReviewsAdapter mReviewsAdapter;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mImagePosterView = (ImageView) findViewById(R.id.title_image);
        mImageBackDropView = (ImageView) findViewById(R.id.backDrop_image);
        mTitleView = (TextView) findViewById(R.id.movie_title);
        mReleaseView = (TextView) findViewById(R.id.release_date);
        mTotalView = (TextView) findViewById(R.id.total_vote_view);
        mLanguageView = (TextView) findViewById(R.id.movie_language);
        mAdultView = (TextView) findViewById(R.id.movie_adult);
        mOverviewView = (TextView) findViewById(R.id.movie_overview);
        mRatingBarView = (RatingBar) findViewById(R.id.movie_rating);

        mTrailerView = (RecyclerView) findViewById(R.id.recyclerview_videos);
        mReviewsView = (RecyclerView) findViewById(R.id.recyclerview_reviews);

        mTrailerAdapter = new TrailerAdapter(this,this);
//        mReviewsAdapter = new ReviewsAdapter();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mTrailerView.setLayoutManager(manager);
        mTrailerView.setHasFixedSize(true);
        mTrailerView.setAdapter(mTrailerAdapter);

        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        mReviewsView.setLayoutManager(manager1);
        mReviewsView.setHasFixedSize(true);
//        mReviewsView.setAdapter(mReviewsAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        updateUi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        int moviesID = intent.getIntExtra("MOVIEID",0);
        loadMovieData(moviesID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie,menu);
        return true;
    }

    private void updateUi(){
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("URL");
        String title = intent.getStringExtra("TITLE");
        String overview = intent.getStringExtra("OVERVIEW");
        String language = intent.getStringExtra("LANGUAGE");
        String release = intent.getStringExtra("RELEASE");
        String backDrop = intent.getStringExtra("BACKDROP");
        boolean adult = intent.getBooleanExtra("ADULT",true);
        String voteAverage = intent.getStringExtra("VOTEAVERAGE");
        int voteCount = intent.getIntExtra("VOTECOUNT",0);

        Picasso.with(this).load("http://image.tmdb.org/t/p/w500"+imageUrl).into(mImagePosterView);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500"+backDrop).into(mImageBackDropView);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        mTitleView.setText(title);
        mReleaseView.setText(release);
        mTotalView.setText(String.valueOf(voteCount));
        mLanguageView.setText(language);
        if (adult) {
            mAdultView.setText(R.string.adult);
        }else {
            mAdultView.setText(R.string.not_adult);
        }
        mOverviewView.setText(overview);

        float rating = Float.valueOf(voteAverage)/2;
        mRatingBarView.setRating(rating);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share){
            String mineType = "text/plain";
            String tittle = "How to Share Intent";
            ShareCompat.IntentBuilder.from(this)
                    .setChooserTitle(tittle)
                    .setType(mineType)
                    .setText(mTitleView.getText())
                    .startChooser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMovieData(int moviesID) {
        new FetchTrailerTask(this).execute(moviesID);
    }

    @Override
    public void onPre() {

    }

    @Override
    public void onCompleted(List<Trailers> trailers) {
        if (trailers != null){
            mTrailerAdapter.setTrailerData(trailers);
        }
    }

    @Override
    public void onTrailerClick(Trailers trailers) {
        String videoId = trailers.getKey();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
        intent.putExtra("VIDEO_ID", videoId);
        startActivity(intent);
    }
}
