package com.sulitous.biti.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    private String posterImage;
    private String title;
    private String overview;
    private String language;
    private String backDropImage;
    private String releaseDate;
    private boolean adult;
    private String voteAverage;
    private int voteCount;

    public Movies() {
    }

    Movies(Parcel parcel) {
        posterImage = parcel.readString();
        title = parcel.readString();
        overview = parcel.readString();
        language = parcel.readString();
        backDropImage = parcel.readString();
        releaseDate = parcel.readString();
        adult = Boolean.valueOf(parcel.readString());
        voteAverage = parcel.readString();
        voteCount = parcel.readInt();
    }

    String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    String getBackDropImage() {
        return backDropImage;
    }

    public void setBackDropImage(String backDropImage) {
        this.backDropImage = backDropImage;
    }

    String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterImage);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(language);
        parcel.writeString(backDropImage);
        parcel.writeString(releaseDate);
        parcel.writeString(String.valueOf(adult));
        parcel.writeString(voteAverage);
        parcel.writeInt(voteCount);
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>(){

        @Override
        public Movies createFromParcel(Parcel parcel) {
            return new Movies(parcel);
        }

        @Override
        public Movies[] newArray(int i) {
            return new Movies[i];
        }
    };
}
