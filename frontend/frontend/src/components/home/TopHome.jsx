import React, { useEffect, useState } from "react";
import fetchTrendingMovies from "../../api/home/Movie";
import "../../style/home/TopHome.css";
import { FaFilm } from "react-icons/fa";

// 무한루프로 코드 변경해야함 !
const TopHome = () => {
  const [movies, setMovies] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const accessToken = import.meta.env.VITE_TMDB_ACCESS_TOKEN;

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const moviePosters = await fetchTrendingMovies(accessToken);
        setMovies(moviePosters);
      } catch (error) {
        console.error(error);
      }
    };

    fetchMovies();
  }, [accessToken]);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % movies.length);
    }, 1500);

    return () => clearInterval(interval);
  }, [movies.length]);

  const renderStars = (rating) => {
    const roundedRating = Math.round(rating * 10) / 10;
    const starCount = Math.round(roundedRating / 2);
    const stars = [];

    for (let i = 0; i < 5; i++) {
      stars.push(i < starCount ? "★" : "☆");
    }

    return (
      <div className="starRating">
        {stars.map((star, index) => (
          <span key={index} className="star">
            {star}
          </span>
        ))}
        <span className="ratingNumber">({roundedRating})</span>
      </div>
    );
  };

  return (
    <div className="topHome">
      <div className="titleContainer">
        <FaFilm className="titleIcon" />
        <div className="titleText">오늘의 Top10 영화</div>
      </div>
      {movies.length === 0 ? (
        <div>포스터를 불러오는 중...</div>
      ) : (
        <div>
          <div
            className="posterGrid"
            style={{
              transform: `translateX(-${currentIndex * 220}px)`,
            }}
          >
            {movies.map((movie, index) => (
              <div key={index} className="posterContainer">
                <div className="rankingBadge">{index + 1}</div>
                <img
                  src={`https://image.tmdb.org/t/p/w500${movie.poster_path}`}
                  className="moviePoster"
                  alt={`Movie ${index + 1}`}
                />
                <div className="movieTitle">{movie.title}</div>
                {renderStars(movie.vote_average)}
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default TopHome;
