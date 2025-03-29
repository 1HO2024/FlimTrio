package com.example.flim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.flim.dto.MovieDetailDTO;
import com.example.flim.mapper.MovieDetailMapper;

@Service
public class MovieDetailService {

	@Autowired
	private MovieDetailMapper movieDetailMapper;


	public List<MovieDetailDTO> getReview(int ID) {
		List<MovieDetailDTO> reviewlist = movieDetailMapper.getReview(ID);
		return reviewlist;
	}


	public boolean writeReview(MovieDetailDTO moviedetaildto, int user_idx) {
		int result = movieDetailMapper.writeReview(moviedetaildto.getId(),
				                                   moviedetaildto.getReview_comment(),
				                                   moviedetaildto.getReview_rating(),
				                                   user_idx);
		return result > 0 ;
	}


	public boolean updateReview(MovieDetailDTO moviedetaildto, int user_idx) {
		int result = movieDetailMapper.updateReview(moviedetaildto.getId(),
				                                    moviedetaildto.getReview_idx(),
                                                    moviedetaildto.getReview_comment(),
                                                    moviedetaildto.getReview_rating(),
                                                    user_idx);
        return result > 0 ;
	}


	public boolean deleteReview(MovieDetailDTO moviedetaildto, int user_idx) {
		int result = movieDetailMapper.deleteReview(moviedetaildto.getId(),
                                                    moviedetaildto.getReview_idx(),
									                user_idx);
        return result > 0 ;
	}


}
