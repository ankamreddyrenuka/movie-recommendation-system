package com.cinematchai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "watchlist_movies")
public class WatchlistMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    private String title;
    private String posterPath;
    private String status;
    private String notes;
    private boolean watched;

    public WatchlistMovie() {
    }

    public WatchlistMovie(String movieId, String title, String posterPath, String status, String notes, boolean watched) {
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.status = status;
        this.notes = notes;
        this.watched = watched;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}
