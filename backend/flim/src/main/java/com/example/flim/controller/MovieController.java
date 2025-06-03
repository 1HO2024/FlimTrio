package com.example.flim.controller;



import com.example.flim.dto.*;
import com.example.flim.service.AuthService;
import com.example.flim.service.MovieService;
import com.example.flim.service.RecommendService;
import com.example.flim.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RecommendService recommendService;
    
    //4.10 일 추가
    @Autowired
	private  AuthService authService;
    
	@Autowired
	private JwtUtil jwtUtil;
	  
    
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

// 4.10 일 토큰로직 추가 
//로그인 한 email 이 있으면 검색기록 저장 , 아니면 저장안함
//영화 제목, 배우 이름으로 조회
@GetMapping("/movies/search")
public ResponseEntity<MovieResponse> searchMovies(@RequestHeader("Authorization") String authorizationHeader,
		                                          @RequestParam("query") String query) {

	// 토큰 인증 후 예외처리 
	int userIdx = 0;  // 기본값 0 
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
    	List<Movie> movies = movieService.searchMovies(query, userIdx);
    	MovieResponse response = new MovieResponse(true, "검색 성공", movies);
    	return ResponseEntity.ok(response);
    }

    String jwtToken = authorizationHeader.substring(7); 
    if (jwtToken.isEmpty()) {
    	List<Movie> movies = movieService.searchMovies(query, userIdx);
    	MovieResponse response = new MovieResponse(true, "검색 성공", movies);
    	return ResponseEntity.ok(response);
    }

    String email = jwtUtil.extractUsername(jwtToken);
    if (email == null) {
    	List<Movie> movies = movieService.searchMovies(query, userIdx);
    	MovieResponse response = new MovieResponse(true, "검색 성공", movies);
    	return ResponseEntity.ok(response);
    }

    UserDetails user = authService.loadUserByUsername(email);
    if (user != null) {
        userIdx = authService.getUserIdx(email);  // 인증 성공 시 userIdx 설정
    } else {
    	List<Movie> movies = movieService.searchMovies(query, userIdx);
    	MovieResponse response = new MovieResponse(true, "검색 성공", movies);
    	return ResponseEntity.ok(response);
    }
	
	System.out.println("검색 요청: query=" + query + ", userIdx=" + userIdx);  // 추가!

    List<Movie> movies = movieService.searchMovies(query, userIdx);
    MovieResponse response = new MovieResponse(true, "검색 성공", movies);
    return ResponseEntity.ok(response);
}

//    장르별 인기 영화 조회
    @GetMapping("/movies/topgenre")
    public ResponseEntity<MovieResponse> getTopGenre(@RequestParam(name = "genreIds") String genreIds) {
        List<Movie> movies = movieService.getTopGenre(genreIds);
        MovieResponse response = new MovieResponse(true,"장르 top10",movies);
        return ResponseEntity.ok(response);
    }

//    영화 추천 (인기도 top 10)
    @GetMapping("/movies/topmovie")
    public ResponseEntity<MovieResponse> getTopMovie() {
        List<Movie> movies = movieService.getTopMovie();
        MovieResponse response = new MovieResponse(true,"인기 영화",movies);
        return ResponseEntity.ok(response);
    }


//    연관검색어
    @GetMapping("/movies/related")
    public ResponseEntity<MovieResponse> getRelatedSearches(@RequestParam String query) {
        RelatedSearchResponse response = movieService.getRelatedSearchResponse(query);
        return ResponseEntity.ok(new MovieResponse(true, "연관 검색어 및 영화 조회 성공", response));
    }

//    =================================== 알고리즘 ============================
    @GetMapping("/movies/recommend")
    public ResponseEntity<MovieResponse> getRecommend(@RequestHeader("Authorization") String authorizationHeader) {
    	 	
    	    // 토큰 인증 과정 
    	    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
		        return ResponseEntity.badRequest().body(new MovieResponse(false, "비로그인/로그인 실패 알고리즘 없음"));
		    }
		    String jwtToken = authorizationHeader.substring(7); 
		    if (jwtToken.isEmpty()) {
		        return ResponseEntity.badRequest().body(new MovieResponse(false, "비로그인/로그인 실패 알고리즘 없음"));
		    }
		    String email = jwtUtil.extractUsername(jwtToken);
		    if (email == null) {
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MovieResponse(false, "비로그인/로그인 실패 알고리즘 없음"));
		    }
		    UserDetails user = authService.loadUserByUsername(email);
		    if (user == null) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MovieResponse(false, "비로그인/로그인 실패 알고리즘 없음"));
		    }
		    int userIdx = authService.getUserIdx(email);
		    
		    //확인용 System.out.println(email);
		    
        List<RecommendedMovieResponse> recommendMovie = recommendService.recommendMovie(userIdx);
        return ResponseEntity.ok(new MovieResponse(true,"알고리즘",recommendMovie));
      }

    }





