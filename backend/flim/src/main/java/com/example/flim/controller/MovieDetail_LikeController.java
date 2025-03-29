package com.example.flim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.flim.service.AuthService;
import com.example.flim.service.MovieDetailService;
import com.example.flim.util.JwtUtil;

@Controller
@RequestMapping("/api/v1/movie-detail/")
public class MovieDetail_LikeController {
    
	@Autowired
	private  AuthService authService;
	
	@Autowired
	private MovieDetailService movieDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	  
	  //좋아요
	  @GetMapping("write-wish")
	  public String writeWish() {
		  
		  return null;
    }
	  //좋아요 취소
	  @GetMapping("delete-wish")
	  public String deleteWish() {
		  
		  return null;
    }
}
