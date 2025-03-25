package com.example.flim.service;

import com.example.flim.dto.Movie;
import com.example.flim.mapper.MovieMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private RestTemplate restTemplate;

    private final String apiKey = "8cddecbeaaf1e1f845bf146c6f747ee1"; // API Key 입력
    private final String URL = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&language=ko-KR&page=";

    @Override
    public void fetchMoviesFromApiAndSave() {
        Gson gson = new Gson();

        for (int page = 1; page <= 500; page++) {
            String requestUrl = URL + page;

            try {
                String response = restTemplate.getForObject(requestUrl, String.class);

                JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                JsonArray results = jsonObject.getAsJsonArray("results");

                for (JsonElement element : results) {
                    JsonObject movieData = element.getAsJsonObject();

                    int movieId = movieData.get("id").getAsInt();

                    // 중복 확인
                    if (movieMapper.checkMovieExists(movieId) == 0) { // 0이면 중복 없음
                        Movie movie = new Movie();
                        movie.setId(movieId);
                        movie.setTitle(movieData.get("title").getAsString());
                        movie.setOverview(movieData.has("overview") && !movieData.get("overview").isJsonNull()
                                ? movieData.get("overview").getAsString()
                                : "No description available");
                        movie.setPopularity(movieData.get("popularity").getAsDouble());
                        movie.setPosterPath(movieData.has("poster_path") && !movieData.get("poster_path").isJsonNull()
                                ? movieData.get("poster_path").getAsString()
                                : null);

                        String releaseDate = movieData.has("release_date") && !movieData.get("release_date").isJsonNull()
                                ? movieData.get("release_date").getAsString()
                                : null;
                        if (releaseDate != null && releaseDate.trim().isEmpty()) {
                            releaseDate = null;
                        }
                        movie.setReleaseDate(releaseDate);

                        // 장르 리스트 JSON 변환 후 저장
                        JsonArray genreArray = movieData.getAsJsonArray("genre_ids");
                        List<Integer> genreIds = new ArrayList<>();
                        for (JsonElement genreId : genreArray) {
                            genreIds.add(genreId.getAsInt());
                        }

                        Gson genreGson = new Gson();
                        String genreIdsJson = genreGson.toJson(genreIds);
                        movie.setGenreIds(genreIdsJson);

                        // ✅ 중복되지 않은 경우에만 INSERT 실행
                        movieMapper.insertMovie(movie);
                    } else {
                        System.out.println("⚠️ 중복된 영화 ID " + movieId + " - 삽입하지 않음");
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieMapper.getAllMovies();
    }

    @Override
    public Movie getMovieById(int id) {
        return movieMapper.getMovieById(id);
    }

    @Override
    public List<Movie> searchMoviesTitle(String title) {
        return movieMapper.searchMovieTitle("%" + title + "%");
    }
}
