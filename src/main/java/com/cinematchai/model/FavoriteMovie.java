package com.cinematchai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_movies")
public class FavoriteMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    private String title;
    private String posterPath;
    private String genres;
    private double rating;
    private String releaseDate;

    public FavoriteMovie() {
    }

    public FavoriteMovie(String movieId, String title, String posterPath, String genres, double rating, String releaseDate) {
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.genres = genres;
        this.rating = rating;
        this.releaseDate = releaseDate;
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
