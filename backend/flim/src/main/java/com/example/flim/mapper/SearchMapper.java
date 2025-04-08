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


}