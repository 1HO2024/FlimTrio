package com.example.flim.mapper;

import com.example.flim.dto.Movie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MovieMapper {
    @Insert("INSERT INTO movie (id, title, overview, tagline, popularity, poster_path, release_date, genre_ids) VALUES (#{id}, #{title}, #{overview},#{tagline}, #{popularity}, #{posterPath}, #{releaseDate}, #{genreIds})")
    void insertMovie(Movie movie);

    @Select("SELECT COUNT(*) FROM movie WHERE id = #{id}")
    int checkMovieExists(int id);

@Select("SELECT * FROM movie")
    List<Movie> getAllMovies();

@Select("SELECT * FROM movie WHERE id = #{id}")
    Movie getMovieById(@Param("id") int id);

    @Select("SELECT * FROM movie WHERE title LIKE CONCAT('%', #{title}, '%')")
    List<Movie> searchMovieTitle(@Param("title") String title);
}

