package com.example.flim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.flim.dto.MovieDetailDTO;
import com.example.flim.dto.MovieDetailResponse;
import com.example.flim.service.AuthService;
import com.example.flim.service.MovieDetailLikeService;
import com.example.flim.service.MovieDetailReviewService;
import com.example.flim.util.JwtUtil;

@Controller
@RequestMapping("/api/v1/movie-detail/")
public class MovieDetailLikeController {
    
	@Autowired
	private  AuthService authService;
	
	@Autowired
	private MovieDetailLikeService movieDetailLikeService;
	
	@Autowired
	private JwtUtil jwtUtil;
	  
	  //좋아요 
	  @PostMapping("toggle-like")
	 public ResponseEntity<MovieDetailResponse> toggleLike(@RequestHeader("Authorization") String authorizationHeader,
                                                                @RequestBody MovieDetailDTO moviedetaildto) {

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
		return ResponseEntity.badRequest().body(new MovieDetailResponse(false, "유효하지 않은 토큰입니다."));
		}
		String jwtToken = authorizationHeader.substring(7);  
		if (jwtToken.isEmpty()) {
		return ResponseEntity.badRequest().body(new MovieDetailResponse(false, "토큰이 필요합니다."));
		}
		String email = jwtUtil.extractUsername(jwtToken);
		if (email == null) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MovieDetailResponse(false, "유효하지 않은 토큰입니다."));
		}
		UserDetails user = authService.loadUserByUsername(email);
		if (user == null) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MovieDetailResponse(false, "사용자를 찾을 수 없습니다."));
		}
		
		int user_idx = authService.getUserIdx(email);
		
		 //좋아요 상태확인 
	    boolean isLiked = movieDetailLikeService.isLiked(moviedetaildto.getId(), user_idx);
	    System.out.println(isLiked);
	    if (isLiked) {
	        // 좋아요 o
	    	movieDetailLikeService.deleteLike(moviedetaildto, user_idx);
	        return ResponseEntity.ok(new MovieDetailResponse(true, "좋아요 취소 성공"));
	    } else {
	        // 좋아요 x 
	    	movieDetailLikeService.writeLike(moviedetaildto, user_idx);
	        return ResponseEntity.ok(new MovieDetailResponse(true, "좋아요 성공"));
	    }
    }
}
