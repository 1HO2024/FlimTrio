package com.example.flim.service;

import com.example.flim.dto.Cast;
import com.example.flim.dto.Crew;
import com.example.flim.dto.Movie;
import com.example.flim.dto.MovieDetailResponse;
import com.example.flim.mapper.CastMapper;
import com.example.flim.mapper.CrewMapper;
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
    private CastMapper castMapper; // 추가된 부분: CastMapper
    @Autowired
    private CrewMapper crewMapper; // 추가된 부분: CrewMapper

    @Autowired
    private RestTemplate restTemplate;

    private final String apiKey = "8cddecbeaaf1e1f845bf146c6f747ee1"; // API Key 입력
    private final String URL = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&language=ko-KR&page=";




    @Override
    public void fetchMoviesFromApiAndSave() {
        Gson gson = new Gson();


        for (int page = 1; page <= 5000; page++) {
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
                        movie.setTitle(movieData.has("title") && !movieData.get("title").isJsonNull()
                                ? movieData.get("title").getAsString()
                                : "No title available");

                        // overview 값 null 체크
                        movie.setOverview(movieData.has("overview") && !movieData.get("overview").isJsonNull()
                                ? movieData.get("overview").getAsString()
                                : "No description available");

                        // popularity 값 null 체크
                        movie.setPopularity(movieData.has("popularity") && !movieData.get("popularity").isJsonNull()
                                ? movieData.get("popularity").getAsDouble()
                                : 0.0);

                        // poster_path 값 null 체크
                        movie.setPosterPath(movieData.has("poster_path") && !movieData.get("poster_path").isJsonNull()
                                ? movieData.get("poster_path").getAsString()
                                : null);

                        // release_date 값 null 체크
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

                        // Cast와 Crew 정보를 추가로 가져와서 저장
                        String movieCreditsUrl = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=" + apiKey;
                        String creditsResponse = restTemplate.getForObject(movieCreditsUrl, String.class);

                        JsonObject creditsData = gson.fromJson(creditsResponse, JsonObject.class);

                        // Cast 정보
                        JsonArray castArray = creditsData.getAsJsonArray("cast");
                        List<Cast> castList = new ArrayList<>();
                        for (JsonElement castElement : castArray) {
                            JsonObject castData = castElement.getAsJsonObject();
                            Cast cast = new Cast();
                            cast.setId(castData.has("id") && !castData.get("id").isJsonNull() ? castData.get("id").getAsInt() : 0);
                            cast.setName(castData.has("name") && !castData.get("name").isJsonNull() ? castData.get("name").getAsString() : "Unknown");
                            cast.setCharacter(castData.has("character") && !castData.get("character").isJsonNull() ? castData.get("character").getAsString() : "No character");
                            cast.setGender(castData.has("gender") && !castData.get("gender").isJsonNull() ? castData.get("gender").getAsInt() : 0);
                            cast.setProfilePath(castData.has("profile_path") && !castData.get("profile_path").isJsonNull() ? castData.get("profile_path").getAsString() : null);
                            cast.setMovieId(movieId);  // 외래키 설정
                            castList.add(cast);
                        }

                        // Crew 정보
                        JsonArray crewArray = creditsData.getAsJsonArray("crew");
                        List<Crew> crewList = new ArrayList<>();
                        for (JsonElement crewElement : crewArray) {
                            JsonObject crewData = crewElement.getAsJsonObject();
                            Crew crew = new Crew();
                            crew.setId(crewData.has("id") && !crewData.get("id").isJsonNull() ? crewData.get("id").getAsInt() : 0);
                            crew.setName(crewData.has("name") && !crewData.get("name").isJsonNull() ? crewData.get("name").getAsString() : "Unknown");
                            crew.setJob(crewData.has("job") && !crewData.get("job").isJsonNull() ? crewData.get("job").getAsString() : "No job");
                            crew.setGender(crewData.has("gender") && !crewData.get("gender").isJsonNull() ? crewData.get("gender").getAsInt() : 0);
                            crew.setProfilePath(crewData.has("profile_path") && !crewData.get("profile_path").isJsonNull() ? crewData.get("profile_path").getAsString() : null);
                            crew.setMovieId(movieId);  // 외래키 설정
                            crewList.add(crew);
                        }

                        // Cast와 Crew 저장
                        for (Cast cast : castList) {
                            castMapper.insertCast(cast);
                        }

                        for (Crew crew : crewList) {
                            crewMapper.insertCrew(crew);
                        }

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
    public MovieDetailResponse getMovieWithCastAndCrewById(int movieId) {
        // 영화 조회
        Movie movie = movieMapper.getMovieById(movieId);

        // 영화가 없다면 null 반환
        if (movie == null) {
            return null;
        }

        // Cast 조회
        List<Cast> castList = castMapper.getCastByMovieId(movieId);

        // Crew 조회
        List<Crew> crewList = crewMapper.getCrewByMovieId(movieId);

        // MovieDetailResponse 반환
        return new MovieDetailResponse(true, "영화 조회 성공", movie, castList, crewList);
    }


    @Override
    public List<Movie> searchMoviesTitle(String title) {
        return movieMapper.searchMovieTitle("%" + title + "%");
    }


}
