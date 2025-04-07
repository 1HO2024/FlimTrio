package com.example.flim.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.flim.dto.Movie;
import com.example.flim.dto.MovieAlgo;

@Mapper
public interface MovieDetailAlgorithmMapper {

	List<MovieAlgo> getMovieforAl(int id);

	List<MovieAlgo> getUserOverview(int id);




}
