package com.sulitous.biti.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;

class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolderView>{

    private Context mContext;
    private List<Movies> movies;

    private final MoviesAdapterOnClickHandler mClickHandler;

    interface MoviesAdapterOnClickHandler{
        void onClick(Movies movies);
    }

    MoviesAdapter(Context context, MoviesAdapterOnClickHandler listener) {
        mContext =context;
        mClickHandler = listener;
    }

    @Override
    public MovieHolderView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.movie_list,viewGroup,false);
        return new MovieHolderView(view);
    }

    @Override
    public void onBindViewHolder(MovieHolderView holder, int position) {
        holder.bindToView(movies.get(position));
    }

    @Override
    public int getItemCount() {
        if (movies == null) {
            return 0;
        }else {
            return movies.size();
        }
    }

    class MovieHolderView extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mPosterView;

        MovieHolderView(View itemView) {
            super(itemView);
            mPosterView = (ImageView) itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        void bindToView(Movies movies){

            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w500"+movies.getPosterImage()).into(mPosterView);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick(movies.get(getAdapterPosition()));
        }
    }

    void setMoviesData(List<Movies> moviesData){
        movies = moviesData;
        notifyDataSetChanged();
    }
}
