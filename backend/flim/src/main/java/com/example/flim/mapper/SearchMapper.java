package com.example.flim.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SearchMapper {

    @Insert("INSERT INTO search_history (email, query,created_at) VALUES (#{email},#{query},NOW())")
    void insertSearchHistory(String email, String query);
}
