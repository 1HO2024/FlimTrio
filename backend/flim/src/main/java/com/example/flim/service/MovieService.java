package com.example.flim.service;


import com.example.flim.dto.Movie;
import com.example.flim.dto.MovieDetailResponse;

import java.util.List;

public interface MovieService {

    void fetchMoviesFromApiAndSave();


    List<Movie> getAllMovies();


    MovieDetailResponse getMovieWithCastAndCrewById(int id);

    List<Movie> searchMovies(String query,String email);

    List<Movie> getTopGenre(String genreIds);

    List<Movie> getTopMovie();
}
