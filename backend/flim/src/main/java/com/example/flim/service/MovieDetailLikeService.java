package com.example.flim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.flim.dto.MovieDetailDTO;
import com.example.flim.mapper.MovieDetailLikeMapper;

@Service
public class MovieDetailLikeService {

	@Autowired
	private MovieDetailLikeMapper movieDetailLikeMapper;

	// 좋아요
	public boolean isLiked(int id, int user_idx) {
		  return  movieDetailLikeMapper.isLike(id, user_idx);
	}	

	public boolean writeLike(MovieDetailDTO moviedetaildto, int user_idx) {
		int result = movieDetailLikeMapper.writeLike(moviedetaildto.getId(),
                                                 user_idx);
        return result > 0 ;
	}


	public boolean deleteLike(MovieDetailDTO moviedetaildto, int user_idx) {
		int result = movieDetailLikeMapper.deleteLike(moviedetaildto.getId(),
                                                 user_idx);
		return result > 0 ;
	}







}
