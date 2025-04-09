package com.example.flim.controller;


import com.example.flim.dto.*;
import com.example.flim.service.MovieService;
import com.example.flim.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RecommendService recommendService;


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

// 로그인 한 email 이 있으면 검색기록 저장 , 아니면 저장안함
//    영화 제목, 배우 이름으로 조회
@GetMapping("/movies/search")
public ResponseEntity<MovieResponse> searchMovies(@RequestParam("query") String query,
                                                  @RequestParam(value ="userIdx", required = false) int userIdx) {
    System.out.println("검색 요청: query=" + query + ", userIdx=" + userIdx);  // 추가!

    List<Movie> movies = movieService.searchMovies(query, userIdx);
    MovieResponse response = new MovieResponse(true, "검색 성공", movies);
    return ResponseEntity.ok(response);
}

//    장르별 인기 영화 조회
    @GetMapping("movies/topgenre")
    public ResponseEntity<MovieResponse> getTopGenre(@RequestParam String genreIds) {
        List<Movie> movies = movieService.getTopGenre(genreIds);
        MovieResponse response = new MovieResponse(true,"장르 top10",movies);
        return ResponseEntity.ok(response);
    }

//    영화 추천 (인기도 top 10)
    @GetMapping("movies/topmovie")
    public ResponseEntity<MovieResponse> getTopMovie() {
        List<Movie> movies = movieService.getTopMovie();
        MovieResponse response = new MovieResponse(true,"인기 영화",movies);
        return ResponseEntity.ok(response);
    }


//    연관검색어
    @GetMapping("movies/related")
    public ResponseEntity<MovieResponse> getRelatedSearches(@RequestParam String query) {
        RelatedSearchResponse response = movieService.getRelatedSearchResponse(query);
        return ResponseEntity.ok(new MovieResponse(true, "연관 검색어 및 영화 조회 성공", response));
    }

//    =================================== 알고리즘 ============================
    @GetMapping("/recommend/{userIdx}")
    public ResponseEntity<MovieResponse> getRecommend(@PathVariable int userIdx) {
        List<RecommendedMovieResponse> recommendMovie = recommendService.recommendMovie(userIdx);
        return ResponseEntity.ok(new MovieResponse(true,"알고리즘",recommendMovie));
    }








    }




