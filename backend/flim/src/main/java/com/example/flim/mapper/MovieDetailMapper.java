package com.example.flim.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.flim.dto.MovieDetailDTO;

@Mapper
public interface MovieDetailMapper {

	List<MovieDetailDTO> getReview(int ID);


	int writeReview(@Param("ID")int ID, 
			        @Param("review_comment")String review_comment, 
			        @Param("review_rating")int review_rating, 
			        @Param("user_idx")int user_idx);


	int updateReview(@Param("ID")int ID, 
           			 @Param("review_idx")int review_idx, 
           			 @Param("review_comment")String review_comment,
           			 @Param("review_rating")int review_rating,
			         @Param("user_idx")int user_idx);


	int deleteReview(@Param("ID")int ID, 
			         @Param("review_idx")int review_idx, 
			         @Param("user_idx")int user_idx);
			                           

}
