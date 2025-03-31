package com.example.flim.service;


import com.example.flim.dto.Movie;
import com.example.flim.dto.MovieDetailResponse;

import java.util.List;

public interface MovieService {

    void fetchMoviesFromApiAndSave();


    List<Movie> getAllMovies();


    List<Movie> searchMoviesTitle(String title);

    MovieDetailResponse getMovieWithCastAndCrewById(int id);
}
