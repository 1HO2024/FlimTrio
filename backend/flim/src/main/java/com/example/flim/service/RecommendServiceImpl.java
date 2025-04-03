package com.example.flim.service;

import com.example.flim.dto.Movie;
import com.example.flim.mapper.MovieMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private MovieMapper movieMapper;

//    @Override
//    public List<Movie> getMovieByGenre(int id) {
//        Movie movies = movieMapper.getMovieById(id);
////        영화 정보 가져오기
//        if (movies == null){
//            return new ArrayList<>();
//        }
//
//        String genreIds = movies.getGenreIds();
//
//        return movieMapper.getMovieByGenre(genreIds);
//    }

//    @Override
//    public List<Movie> getMoviesBySearch(String title, String memberId) {
//        movieMapper.saveUserSearchHistory(title,memberId);
//        return movieMapper.searchMovieTitle("%" + title + "%");
//    }

//    @Override
//    public List<Movie> getMovieByFavorite(String memberId) {
//        return List.of();
//    }
}

