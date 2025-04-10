package com.example.flim.controller;


import com.example.flim.dto.Movie;
import com.example.flim.dto.MovieAlgoResponse;
import com.example.flim.dto.MovieDetailResponse;
import com.example.flim.dto.MovieResponse;
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
public ResponseEntity<MovieDetailResponse> getMovieWithCastAndCrew(@PathVariable("id") int id) {
    MovieDetailResponse response = movieService.getMovieWithCastAndCrewById(id);

    if (response == null) {
        return ResponseEntity.notFound().build();
    }
    //이다음에 진행
    return ResponseEntity.ok(response);
}

//    영화 제목, 배우 이름으로 조회
    @GetMapping("/movies/search")
    public ResponseEntity<MovieResponse> searchMovies(@RequestParam("query") String query) {
        List<Movie> movies = movieService.searchMovies(query);
        MovieResponse response = new MovieResponse(true,"영화 조회 성공",movies);
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


//    =================================== 알고리즘 ============================
//    사용자가 특정 영화를 클릭하면 해당 영화의 장르를 기반으로 추천
//    @GetMapping("/genre/{id}")
//    public MovieResponse getMovieByGenre(@PathVariable int id) {
//        List<Movie> movies = recommendService.getMovieByGenre(id);
//        return new MovieResponse(true,"장르 기반 영화 추천", movies);
//    }
//
////    사용자가 검색한 영화 제목을 기반으로 추천
//    @GetMapping("/search")
//    public MovieResponse getMovieBySearch(@RequestParam("title") String title,@RequestParam("memberId")String memberId) {
//        List<Movie> movies = recommendService.getMoviesBySearch(title, memberId);
//        return new MovieResponse(true, "검색어 기반 영화 추천", movies);
//    }
//
////    로그인한 사용자의 최근 검색기록과 선호 장르르 반영한 맞춤형 추천
//    @GetMapping("/favorite")
//    public MovieResponse getMovieByFavorite(@RequestParam String memberId) {
//        List<Movie> movies = recommendService.getMovieByFavorite(memberId);
//        return new MovieResponse(true,"사용자 맞춤 추천 목록",movies);
//    }







    }




