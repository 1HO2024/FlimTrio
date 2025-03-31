package com.example.flim.dto;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class Movie {
    private int id;
    private String genreIds;
    private String title;
    private String tagline;
    private String overview;
    private String posterPath;
    private double popularity;
    private String releaseDate;

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
        genreMap.put(10752, "저장");
        genreMap.put(37, "서부");
    }

    // 장르 ID 목록을 이름으로 변환
    public void setGenreIds(String genreIds) {
        if (genreIds != null && !genreIds.isEmpty()) {
            String[] ids = genreIds.split(",");
            List<String> genreNames = Arrays.stream(ids)
                    .map(id -> {
                        try {
                            // id가 숫자인지 체크하고, 숫자가 아니면 null 처리
                            int genreId = Integer.parseInt(id);
                            return genreMap.get(genreId); // 유효한 genreId를 가져옴
                        } catch (NumberFormatException e) {
                            // 숫자가 아니면 null 반환
                            return null;
                        }
                    })
                    .filter(Objects::nonNull) // null을 필터링 (유효한 ID만 변환)
                    .collect(Collectors.toList());

            // 장르 이름을 "장르1,장르2,장르3" 형태로 변환
            this.genreIds = String.join(",", genreNames);
        }
    }
}