package com.example.flim.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.flim.dto.ApiResponse;
import com.example.flim.dto.SearchPassResponse;
import com.example.flim.dto.SignResponse;
import com.example.flim.dto.UserDTO;
import com.example.flim.service.AuthService;
import com.example.flim.util.JwtUtil;

@Controller
@RequestMapping("/api/v1")
public class SignController {
	
	@Autowired
	private  AuthService authService;
	
	@Autowired
	private JwtUtil jwtUtil;
	

    
	//회원가입
	@PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserDTO userDTO) {
		authService.registerUser(userDTO);
		//true 랑 메세지 담을려고 객체만듬 
		ApiResponse  response = new ApiResponse(true, "회원 가입 성공");
	    return ResponseEntity.ok(response); 
    }
	

	
	//로그인
	@PostMapping("/signin")
	public ResponseEntity<SignResponse> signin(@RequestBody UserDTO userDTO) {		
		 String username = userDTO.getEmail();
		 
		 // 이메일과 비밀번호 검증
	     boolean isValidUser = authService.authenticateUser(userDTO.getEmail(), userDTO.getPassword());
	        
	     if (!isValidUser) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                 .body(new SignResponse(false, "로그인 실패: 이메일 또는 비밀번호가 올바르지 않습니다.", null, null, null));
	   }
	     
	     //토큰 생성
		 String accessToken  = jwtUtil.generateToken(userDTO.getEmail()); 
		 String refreshToken = jwtUtil.generateRefreshToken(username);
		 
		 
		 //정보 가져오기(닉네임,전번)
	     String nickname     = authService.getNickname(userDTO.getEmail());
	     String phoneNumber  = authService.getPhonenumber(userDTO.getEmail());
	        
	     
	     Map<String, String> userData = new HashMap<>();
	     userData.put("nickname", nickname); 
	     userData.put("email", userDTO.getEmail());
	     userData.put("phoneNumber", phoneNumber); 

	     SignResponse response = new SignResponse(true, "로그인 성공", userData, accessToken, refreshToken );
	     return ResponseEntity.ok(response); 
	   }
	
		 // 리프레시 토큰을 사용하여 액세스 토큰 재발급
	    @PostMapping("/refresh")
	    public ResponseEntity<SignResponse> refreshAccessToken(@RequestHeader("Authorization") String authorizationHeader) {
	        try {
	            // 리프레시 토큰으로 새로운 액세스 토큰 발급
	        	String refreshToken = authorizationHeader.substring(7);
	            
	        	System.out.println(refreshToken);
	            String newAccessToken = jwtUtil.refreshAccessToken(refreshToken);
	            String username = jwtUtil.extractUsername(refreshToken);
	            String newRefreshToken = jwtUtil.generateRefreshToken(username); 
	            
	            // 응답으로 새로운 액세스 토큰을 반환
	            return ResponseEntity.ok(new SignResponse(true, "액세스 토큰 재발급 성공", null, newAccessToken, newRefreshToken));
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(new SignResponse(false, "리프레시 토큰이 유효하지 않거나 만료되었습니다.", null, null, null));
	        }
	    }
	
	

	  //비밀번호 찾기
	   @PostMapping("/search-password")
	   public ResponseEntity<SearchPassResponse> searchPassword(@RequestBody UserDTO userDTO){
		   String tempPassword =  authService.searchPassword(userDTO); 
		   
		   if (tempPassword == null) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND)
		                .body(new SearchPassResponse(false, "사용자를 찾을 수 없습니다."));
		    }

		    return ResponseEntity.ok(new SearchPassResponse(true, "임시 비밀번호가 발급되었습니다. 로그인 후 꼭 변경해주세요", tempPassword));
	   }
	   
	
	   
}