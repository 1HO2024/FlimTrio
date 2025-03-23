package com.example.flim.mapper;

import com.example.flim.dto.Movie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MovieMapper {
    @Insert("INSERT INTO movie (id, title, overview, popularity, poster_path, release_date, genre_ids) VALUES (#{id}, #{title}, #{overview}, #{popularity}, #{posterPath}, #{releaseDate}, #{genreIds})")
    void insertMovie(Movie movie);

    @Select("SELECT COUNT(*) FROM movie WHERE id = #{id}")
    int checkMovieExists(int id);



}

