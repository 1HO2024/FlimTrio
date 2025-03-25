package com.example.flim.dto;

import lombok.Data;

@Data
public class Movie {
    private int id;
    private String genreIds;
    private String title;
    private String overview;
    private String posterPath;
    private double popularity;
    private String releaseDate;
}
