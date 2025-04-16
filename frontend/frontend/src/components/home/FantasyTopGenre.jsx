import React, { useEffect, useState } from "react";
import fetchTopGenre from "../../api/movie/topGenre";
import "../../style/home/TopHome.css";
import { FaFilm } from "react-icons/fa";

const FantasyTopGenre = () => {
  const [genreMovies, setGenreMovies] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const movies = await fetchTopGenre("판타지");
        setGenreMovies(movies);
      } catch (error) {
        console.error(error);
      }
    };

    fetchMovies();
  }, []);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % genreMovies.length);
    }, 1500);

    return () => clearInterval(interval);
  }, [genreMovies]);

  return (
    <div className="topHome">
      <div className="titleContainer">
        <FaFilm className="titleIcon" />
        <div className="titleText">판타지 Top {genreMovies.length} 영화</div>
      </div>
      <div
        className="posterGrid"
        style={{
          transform: `translateX(-${currentIndex * 220}px)`,
        }}
      >
        {genreMovies.map((movie, index) => (
          <div key={index} className="posterContainer">
            <div className="rankingBadge">{index + 1}</div>
            {movie.posterPath ? (
              <img
                src={`https://image.tmdb.org/t/p/w500${movie.posterPath}`}
                className="moviePoster"
                alt={`Movie ${index + 1}`}
              />
            ) : (
              <div className="noPoster">포스터 없음</div>
            )}
            <div className="movieTitle">{movie.title}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FantasyTopGenre;
