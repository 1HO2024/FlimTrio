package com.example.flim.service;


import com.example.flim.dto.Movie;

import java.util.List;

public interface MovieService {

    void fetchMoviesFromApiAndSave();


    List<Movie> getAllMovies();

    Movie getMovieById(int id);

    List<Movie> searchMoviesTitle(String title);
}
