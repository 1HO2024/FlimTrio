package com.example.flim.controller;

import com.example.flim.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.flim.dto.ApiResponse;
import com.example.flim.dto.MovieDetailDTO;
import com.example.flim.service.AuthService;
import com.example.flim.service.MovieDetailLikeService;
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
	 public ResponseEntity<ApiResponse> toggleLike(@RequestHeader("Authorization") String authorizationHeader,
                                                                @RequestBody MovieDetailDTO moviedetaildto) {

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
		return ResponseEntity.badRequest().body(new ApiResponse(false, "유효하지 않은 토큰입니다."));
		}
	
		String jwtToken = authorizationHeader.substring(7);  
		if (jwtToken.isEmpty()) {
		return ResponseEntity.badRequest().body(new ApiResponse(false, "토큰이 필요합니다."));
		}
		
		String email = jwtUtil.extractUsername(jwtToken);
		if (email == null) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "유효하지 않은 토큰입니다."));
		}
		
		UserDetails user = authService.loadUserByUsername(email);
		if (user == null) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "사용자를 찾을 수 없습니다."));
		}
		
		int user_idx = authService.getUserIdx(email);
		
		 //좋아요 상태확인 
	    boolean isLiked = movieDetailLikeService.isLiked(moviedetaildto.getId(), user_idx);
	    if (isLiked) {
	        // 좋아요 o

	    	String likestatus = movieDetailLikeService.getLikeStatus(moviedetaildto.getId(), user_idx);
	    	System.out.println(likestatus);
	    	if(likestatus.equals("Like")) {
	    		movieDetailLikeService.deleteLike(moviedetaildto, user_idx);
	    		return ResponseEntity.ok(new ApiResponse(true, "좋아요 취소 성공"));
	    	}else{
	    		movieDetailLikeService.updateLike(moviedetaildto, user_idx);	
	    		return ResponseEntity.ok(new ApiResponse(true, "다시 좋아요 성공"));
	    	}
	    } else {
	        // 좋아요 x 
	    	movieDetailLikeService.writeLike(moviedetaildto, user_idx);
	        return ResponseEntity.ok(new ApiResponse(true, "좋아요 성공"));
	    }
    }
}
