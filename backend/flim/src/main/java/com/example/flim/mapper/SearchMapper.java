package com.example.flim.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {

    @Insert("INSERT INTO search_history (user_idx, query,created_at) VALUES (#{userIdx},#{query},NOW())")
    void insertSearchHistory(@Param("userIdx") int userIdx, @Param("query") String query);


    @Insert("INSERT INTO search_result (search_history_id, movie_id, title, genre_ids, poster_path, keyword) " +
            "VALUES (#{searchHistoryId}, #{movieId}, #{title}, #{genreIds}, #{posterPath}, #{keyword})")
    void insertSearchResult(@Param("searchHistoryId") int searchHistoryId,
                            @Param("movieId") int movieId,
                            @Param("title") String title,
                            @Param("genreIds") String genreIds,
                            @Param("posterPath") String posterPath,
                            @Param("keyword") String keyword);


    @Select("SELECT MAX(search_id) FROM search_history WHERE user_idx = #{userIdx}")
    Integer getSearchHistory(int userIdx);

    @Select("SELECT COUNT(*) FROM search_result WHERE search_history_id = #{searchHistoryId} AND movie_id = #{movieId}")
    int countSearchResult(@Param("searchHistoryId") int searchHistoryId,
                          @Param("movieId") int movieId);
}
