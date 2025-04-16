package com.example.flim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.flim.dto.MovieDetailDTO;
import com.example.flim.dto.MovieDetailResponse;
import com.example.flim.dto.MypageLikeDTO;
import com.example.flim.dto.MypageLikeResponse;
import com.example.flim.dto.MypageReviewDTO;
import com.example.flim.dto.MypageReviewResponse;
import com.example.flim.service.AuthService;
import com.example.flim.service.MovieDetailLikeService;
import com.example.flim.service.MypageService;
import com.example.flim.util.JwtUtil;

@Controller
@RequestMapping("/api/v1/mypage")
public class MypageController {

	@Autowired
	private  AuthService authService;
	
	@Autowired
	private MypageService mypageService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	 @GetMapping("/search-review")
	 public ResponseEntity<MypageReviewResponse> searchReview(@RequestHeader("Authorization") String authorizationHeader,
			                                           MypageReviewDTO mypageDTO) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
		return ResponseEntity.badRequest().body(new MypageReviewResponse(false, "유효하지 않은 토큰입니다."));
		}
		
		String jwtToken = authorizationHeader.substring(7);  
		if (jwtToken.isEmpty()) {
		return ResponseEntity.badRequest().body(new MypageReviewResponse(false, "토큰이 필요합니다."));
		}
		
		String email = jwtUtil.extractUsername(jwtToken);
		if (email == null) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MypageReviewResponse(false, "유효하지 않은 토큰입니다."));
		}
		
		
		UserDetails user = authService.loadUserByUsername(email);
		if (user == null) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MypageReviewResponse(false, "사용자를 찾을 수 없습니다."));
		}
		
		int user_idx = authService.getUserIdx(email);
		 //(해당유저)리뷰 가져오기
	    List<MypageReviewDTO> data = mypageService.searchReview(user_idx);
	    System.out.println(data);
	    
	    if (data != null && !data.isEmpty()) {
	        return ResponseEntity.ok(new MypageReviewResponse(true, "조회 성공", data));
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MypageReviewResponse(false, "리뷰 내역 없음"));
	    }
    }
	 
	 @GetMapping("/search-like")
	 public ResponseEntity<MypageLikeResponse> searchLike(@RequestHeader("Authorization") String authorizationHeader,
			 MypageReviewDTO mypageDTO) {
		 if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			 return ResponseEntity.badRequest().body(new MypageLikeResponse(false, "유효하지 않은 토큰입니다."));
		 }
		 
		 String jwtToken = authorizationHeader.substring(7);  
		 if (jwtToken.isEmpty()) {
			 return ResponseEntity.badRequest().body(new MypageLikeResponse(false, "토큰이 필요합니다."));
		 }
		 
		 String email = jwtUtil.extractUsername(jwtToken);
		 if (email == null) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MypageLikeResponse(false, "유효하지 않은 토큰입니다."));
		 }
		 
		 UserDetails user = authService.loadUserByUsername(email);
		 if (user == null) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MypageLikeResponse(false, "사용자를 찾을 수 없습니다."));
		 }
		 
		 int user_idx = authService.getUserIdx(email);
		 //(해당유저)좋아요 가져오기
		 List<MypageLikeDTO> data = mypageService.searchLike(user_idx);
		 System.out.println(data);
		 
		 if (data != null && !data.isEmpty()) {
			 return ResponseEntity.ok(new MypageLikeResponse(true, "좋아요 조회 성공", data));
		 } else {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MypageLikeResponse(true, "좋아요 내역이 없음"));
		 }
	 }
}