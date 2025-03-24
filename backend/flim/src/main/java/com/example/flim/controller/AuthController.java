package com.example.flim.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.flim.dto.ApiResponse;
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
		    String token = jwtUtil.generateToken(loginRequest.getEmail());
	        
		    //정보 가져오기(닉네임,전번)
	        String nickname = authService.getNickname(loginRequest.getEmail());
	        String phoneNumber = authService.getPhonenumber(loginRequest.getEmail());
	        
	        Map<String, String> userData = new HashMap<>();
	        userData.put("nickname", nickname); 
	        userData.put("email", loginRequest.getEmail());
	        userData.put("phoneNumber", phoneNumber); 

	        
	        LoginResponse response = new LoginResponse(true, "로그인 성공", userData, "Bearer " + token);

	        return ResponseEntity.ok(response); 
	    }
}