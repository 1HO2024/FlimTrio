package com.example.flim.controller;


import com.example.flim.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/fetch-movies")
    public String fetchMovies() {
        movieService.fetchMoviesFromApiAndSave();
        return "Movies fetched and saved!";
    }


}
