package com.example.flim.controller;


import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.flim.config.CustomUserDetails;
import com.example.flim.dto.ApiResponse;
import com.example.flim.dto.AuthResponse;
import com.example.flim.dto.LoginRequest;
import com.example.flim.dto.LoginResponse;
import com.example.flim.dto.UserDTO;
import com.example.flim.service.AuthService;
import com.example.flim.util.JwtUtil;

@Controller
@RequestMapping("/api/v1")
public class AuthController {
	
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
	public ResponseEntity<LoginResponse> signin(@RequestBody LoginRequest loginRequest) {
	       
		 //토큰 생성
		 String token        = jwtUtil.generateToken(loginRequest.getEmail());
		 //정보 가져오기(닉네임,전번)
	     String nickname     = authService.getNickname(loginRequest.getEmail());
	     String phoneNumber  = authService.getPhonenumber(loginRequest.getEmail());
	        
	     
		 // 이메일과 비밀번호 검증
	     boolean isValidUser = authService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
	        
	     if (!isValidUser) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                 .body(new LoginResponse(false, "로그인 실패: 이메일 또는 비밀번호가 올바르지 않습니다.", null, null));
	   }
	     
	     Map<String, String> userData = new HashMap<>();
	     userData.put("nickname", nickname); 
	     userData.put("email", loginRequest.getEmail());
	     userData.put("phoneNumber", phoneNumber); 

	     LoginResponse response = new LoginResponse(true, "로그인 성공", userData, "Bearer " + token);
	     return ResponseEntity.ok(response); 
	   }
	
	   //로그아웃
	   @PostMapping("/signout")
	   public ResponseEntity<ApiResponse> signout() {
	       return ResponseEntity.ok(new ApiResponse(true, "로그아웃 성공"));
	   }
	   
	   //비밀번호 찾기
	   @PostMapping("/search-password")
	   public ResponseEntity<ApiResponse> searchPassword(@RequestBody UserDTO userDTO){
		   String tempPassword =  authService.searchPassword(userDTO); 
		   
		   if (tempPassword == null) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND)
		                .body(new ApiResponse(false, "사용자를 찾을 수 없습니다."));
		    }

		    return ResponseEntity.ok(new ApiResponse(true, "임시 비밀번호가 발급되었습니다.", tempPassword));
	   }
	   
	   //회원정보 조회
	   @GetMapping("/search-profile")
	   public ResponseEntity<ApiResponse> searchProfile(@RequestHeader("Authorization") String authorizationHeader) {
		   // Authorization 헤더에서 "Bearer" 제거 후 JWT 토큰 추출
		    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
		        return ResponseEntity.badRequest().body(new ApiResponse(false, "유효하지 않은 토큰입니다."));
		    }
		    String jwtToken = authorizationHeader.substring(7);  // "Bearer " 이후의 부분 추출
		    if (jwtToken.isEmpty()) {
		        return ResponseEntity.badRequest().body(new ApiResponse(false, "토큰이 필요합니다."));
		    }
		    // JWT 토큰을 사용하여 사용자 이름 추출
		    String email = jwtUtil.extractUsername(jwtToken);
		    if (email == null) {
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "유효하지 않은 토큰입니다."));
		    }

		    /* email 로 사용자 정보 조회 responsebody 에 username 에 담아서 보내는걸 
		     email 에 담아서 보내기 위해 customyserDetail 을 config에 정의해둠*/ 
		    CustomUserDetails user = (CustomUserDetails) authService.loadUserByUsername(email);
		    
		    if (user == null) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "사용자를 찾을 수 없습니다."));
		    }
		    return ResponseEntity.ok(new ApiResponse(true, "회원 정보 조회 성공", user));
		}
}