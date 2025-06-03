package com.example.flim.mapper;

import com.example.flim.dto.Movie;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MovieMapper {
    @Insert("""
        INSERT INTO movie (id, title, overview, popularity, poster_path, release_date, genre_ids)
        VALUES (#{id}, #{title}, #{overview}, #{popularity}, #{posterPath}, #{releaseDate}, #{genreIds})
        ON DUPLICATE KEY UPDATE
        title = VALUES(title),
        overview = VALUES(overview),
        popularity = VALUES(popularity),
        poster_path = VALUES(poster_path),
        release_date = VALUES(release_date),
        genre_ids = VALUES(genre_ids)
    """)
    void insertMovie(Movie movie);


    @Select("SELECT COUNT(*) FROM movie WHERE id = #{id}")
    int checkMovieExists(int id);


    @Select("SELECT id, title, overview, poster_path, popularity, release_date, genre_ids FROM movie")
    @Results(id = "MovieMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "overview", column = "overview"),
            @Result(property = "posterPath", column = "poster_path"),
            @Result(property = "popularity", column = "popularity"),
            @Result(property = "releaseDate", column = "release_date"),
            @Result(property = "genreIds", column = "genre_ids")
    })
    List<Movie> getAllMovies();


    @Select("SELECT * FROM movie WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "overview", column = "overview"),
            @Result(property = "posterPath", column = "poster_path"),
            @Result(property = "popularity", column = "popularity"),
            @Result(property = "releaseDate", column = "release_date"),
            @Result(property = "genreIds", column = "genre_ids")
    })

    Movie getMovieById(@Param("id") int id);



    @Select("""
    SELECT m.*, c.id AS cast_id, c.name AS cast_name, c.character AS cast_character, 
           c.gender AS cast_gender, c.profile_path AS cast_profilePath
    FROM movie m
    LEFT JOIN cast c ON m.id = c.movie_id
    WHERE m.title LIKE #{query} OR c.name LIKE #{query}
""")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "overview", column = "overview"),
            @Result(property = "posterPath", column = "poster_path"),
            @Result(property = "popularity", column = "popularity"),
            @Result(property = "releaseDate", column = "release_date"),
            @Result(property = "genreIds", column = "genre_ids"),
            // 🔥 배우 정보 매핑
            @Result(property = "castList", javaType = List.class, column = "id",
                    many = @Many(select = "com.example.flim.mapper.CastMapper.getCastsByMovieId"))
    })
    List<Movie> searchMovies(@Param("query") String query);





    @Select("""
        SELECT * FROM movie
        WHERE genre_ids LIKE CONCAT('%' , #{genreIds}, '%')
        ORDER BY popularity DESC
        LIMIT 10
    """)
    List<Movie> topgenre(@Param("genreIds") String genreIds);

    @Select("SELECT * FROM movie ORDER BY popularity DESC LIMIT 10")
    List<Movie> getTopMovie();


    @Select("""
        SELECT *
        FROM movie
        WHERE title LIKE CONCAT('%', #{query}, '%')
        ORDER BY popularity DESC
        LIMIT 10
    """)
    List<Movie> findRelatedMoviesByTitle(@Param("query") String query);

    @Select("""
        SELECT title
        FROM movie
        WHERE title LIKE CONCAT('%', #{query}, '%')
        ORDER BY popularity DESC
        LIMIT 10
    """)
    List<String> findRelatedQueriesFromTitle(@Param("query") String query);
}




