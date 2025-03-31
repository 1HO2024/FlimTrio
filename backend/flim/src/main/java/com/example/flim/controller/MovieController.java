package com.example.flim.controller;


import com.example.flim.dto.Movie;
import com.example.flim.dto.MovieDetailResponse;
import com.example.flim.dto.MovieResponse;
import com.example.flim.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping("/fetch-movies")
    public String fetchMovies() {
        movieService.fetchMoviesFromApiAndSave();
        return "Movies fetched and saved!";
    }

//    모든 영화 조회
    @GetMapping("/movies")
    public ResponseEntity<MovieResponse> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        MovieResponse response = new MovieResponse(true,"영화 목록 조회 성공",movies);
        return ResponseEntity.ok(response);
    }

//    특정 영화 조회
@GetMapping("/movies/{id}")
public ResponseEntity<MovieDetailResponse> getMovieWithCastAndCrew(@PathVariable int id) {
    MovieDetailResponse response = movieService.getMovieWithCastAndCrewById(id);

    if (response == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(response);
}

//    영화 제목으로 조회
    @GetMapping("/movies/search")
    public ResponseEntity<MovieResponse> searchMoviesTitle(@RequestParam("title") String title) {
        List<Movie> movies = movieService.searchMoviesTitle(title);
        MovieResponse response = new MovieResponse(true,"영화 제목 조회 성공",movies);
        return ResponseEntity.ok(response);
    }



}
