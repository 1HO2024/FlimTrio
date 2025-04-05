package com.example.flim.dto;

import lombok.Data;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import javax.persistence.Column;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Movie {
    private int id;
    @Column(name = "genre_ids")  // 🔹 컬럼명 명시
    private String genreIds;
    private String title;
    private String tagline;
    private String overview;
    private String posterPath;
    private double popularity;
    private String releaseDate;


    private List<Cast> castList;


    private static final Map<Integer, String> genreMap = new HashMap<>();

    static {
        // 장르 ID와 이름을 매핑
        genreMap.put(28, "액션");
        genreMap.put(12, "어드벤쳐");
        genreMap.put(16, "애니메이션");
        genreMap.put(35, "코미디");
        genreMap.put(80, "범죄");
        genreMap.put(99, "다큐멘터리");
        genreMap.put(18, "드라마");
        genreMap.put(10751, "가족");
        genreMap.put(14, "판타지");
        genreMap.put(36, "역사");
        genreMap.put(27, "공포");
        genreMap.put(10402, "음악");
        genreMap.put(9648, "미스테리");
        genreMap.put(10749, "로맨스");
        genreMap.put(878, "공상과학");
        genreMap.put(10770, "TV영화");
        genreMap.put(53, "스릴러");
        genreMap.put(10752, "전쟁");
        genreMap.put(37, "서부");
    }

    // 🔹 장르 ID 목록을 이름으로 변환하여 저장
    public void setGenreIds(String genreIds) {
        this.genreIds = genreIds; // 변환 없이 그대로 저장
    }

    public String getGenreIds() {
        return this.genreIds; // 그대로 반환
    }
}

