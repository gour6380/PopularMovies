package com.sulitous.biti.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolderView>{

    private Context mContext;
    private List<Trailers> trailers;

    private final TrailerAdapterOnClickHandler mClickHandler;
    interface TrailerAdapterOnClickHandler{
        void onTrailerClick(Trailers trailers);
    }
    TrailerAdapter(Context mContext, TrailerAdapterOnClickHandler listener) {
        this.mContext = mContext;
        this.mClickHandler = listener;
    }

    @Override
    public TrailerHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trailer_list,parent,false);
        return new TrailerHolderView(view);
    }

    @Override
    public void onBindViewHolder(TrailerHolderView holder, int position) {
        holder.bindToView(trailers.get(position));
    }

    @Override
    public int getItemCount() {
        if (trailers == null) {
            return 0;
        }else {
            return trailers.size();
        }
    }

    class TrailerHolderView extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mName;
        private ImageView mImageView;

        TrailerHolderView(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.trailer_name);
            mImageView = (ImageView) itemView.findViewById(R.id.play_trailer);
            mImageView.setOnClickListener(this);
        }

        void bindToView(Trailers trailers) {
            mName.setText(trailers.getName());
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onTrailerClick(trailers.get(getAdapterPosition()));
        }
    }

    void setTrailerData(List<Trailers> trailersData){
        trailers = trailersData;
        notifyDataSetChanged();
    }
}
