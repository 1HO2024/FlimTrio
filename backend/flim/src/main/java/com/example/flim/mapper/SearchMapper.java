package com.example.flim.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SearchMapper {

    @Insert("INSERT INTO search_history (email, query,created_at) VALUES (#{email},#{query},NOW())")
    void insertSearchHistory(@Param("email") String email,@Param("query") String query);
}
