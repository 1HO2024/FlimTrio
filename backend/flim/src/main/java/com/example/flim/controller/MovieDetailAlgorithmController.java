package com.example.flim.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flim.dto.MovieAlgo;
import com.example.flim.dto.MovieDetailDTO;
import com.example.flim.mapper.MovieDetailAlgorithmMapper;

@RestController
@RequestMapping("/api/v1")
public class MovieDetailAlgorithmController {

    @Autowired
    MovieDetailAlgorithmMapper movieDetailAlMapper;

    @PostMapping("/al")
    public List<String> algorithmshoot(@RequestBody MovieDetailDTO movieDetailDTO) {
        System.out.println(movieDetailDTO.getId());
        
        // 1. 장르로 1차 정리한 데이터 가져오기
        List<MovieAlgo> movies = movieDetailAlMapper.getMovieforAl(movieDetailDTO.getId());     
        List<String> movieOverview = new ArrayList<>();
        for (MovieAlgo movie : movies) {
        	movieOverview.add("==="); 
        	movieOverview.add("title:"+movie.getTitle()); 
            movieOverview.add("overview:"+movie.getOverview()); 
        }
        //디버깅: System.out.println(" 영화줄거리:" + movieOverview);
        
        //유저가 선택한 영화 데이터
        List<MovieAlgo> userMovie = movieDetailAlMapper.getUserOverview(movieDetailDTO.getId());    
        List<String> userMovieOverview = new ArrayList<>();
        for (MovieAlgo movie : userMovie) {
        	userMovieOverview.add("title:"+movie.getTitle()); 
        	userMovieOverview.add("overview:"+movie.getOverview()); 
        }
        //디버깅: System.out.println(" 선택한 영화줄거리:" + userMovieOverview);
        
        //파이썬 호출
        List<String> similarMovies = callPythonScript(userMovieOverview, movieOverview);
        return similarMovies;  
    }

    // 파이썬 시작
    private List<String> callPythonScript(List<String> userMovieOverview, List<String> movieOverview) {
        List<String> recommendedMovies = new ArrayList<>();
        try {
            // 1.내용 저장(temp 저장되는 위치에 텍스트파일로)
            String tempDir = System.getProperty("java.io.tmpdir");
            String userOverviewFile = tempDir + "user_overview.txt";
            String movieOverviewFile = tempDir + "movie_overview.txt";
            
            //유저가 선택한 데이터
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userOverviewFile), StandardCharsets.UTF_8))) {
            	for (String overview : userMovieOverview) {
                    writer.write(overview);
                    writer.newLine();
                }
            }
            //정리한 데이터
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(movieOverviewFile), StandardCharsets.UTF_8))) {
                for (String overview : movieOverview) {
                    writer.write(overview);
                    writer.newLine(); 
                }
            }

            //2. 실행
            String command = "python src/python/MovieDetail_Algorithm.py "
                             + "\"" + userOverviewFile + "\" "
                             + "\"" + movieOverviewFile + "\"";

           //디버깅: System.out.println("호출: " + command);  

            ProcessBuilder processBuilder = new ProcessBuilder(command.toString().split(" "));
            processBuilder.redirectErrorStream(true); // 에러도 동일한 스트림으로 리디렉션
            Process process = processBuilder.start();

            //3.결과
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            process.waitFor();  // 프로세스 종료 대기

            // 4.반환
            String[] similarities = result.toString().split("\n");
            for (String sim : similarities) {
                recommendedMovies.add(sim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recommendedMovies;
    }
}