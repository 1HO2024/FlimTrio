import React, { useEffect, useState } from "react";
import fetchTrendingMovies from "../../api/home/Movie";
import "../../style/home/TopHome.css";
import { FaFilm, FaArrowLeft, FaArrowRight } from "react-icons/fa";

const TopHome = () => {
  // 무한루프 되게 코드 다시 짜야 됨 !

  const [posters, setPosters] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const accessToken = import.meta.env.VITE_TMDB_ACCESS_TOKEN;

  useEffect(() => {
    const fetchPosters = async () => {
      try {
        const moviePosters = await fetchTrendingMovies(accessToken);

        const postersArray = moviePosters.map((movie) => {
          return `https://image.tmdb.org/t/p/w500${movie.poster_path}`;
        });

        setPosters(postersArray);
      } catch (error) {
        console.error(error);
      }
    };

    fetchPosters();
  }, [accessToken]);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % posters.length);
    }, 1500);

    return () => clearInterval(interval);
  }, [posters.length]);

  const handlePrevClick = () => {
    setCurrentIndex(
      (prevIndex) => (prevIndex - 1 + posters.length) % posters.length
    );
  };

  const handleNextClick = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % posters.length);
  };

  return (
    <div className="topHome">
      <div className="titleContainer">
        <FaFilm className="titleIcon" />
        <div className="titleText">오늘의 Top10 영화</div>
      </div>
      {posters.length === 0 ? (
        <div>포스터를 불러오는 중</div>
      ) : (
        <div className="carouselContainer">
          <div
            className="posterGrid"
            style={{
              transform: `translateX(-${currentIndex * 220}px)`,
            }}
          >
            {posters.map((poster, index) => (
              <img key={index} src={poster} className="moviePoster" />
            ))}
          </div>

          {currentIndex > 0 && (
            <FaArrowLeft className="arrow left" onClick={handlePrevClick} />
          )}

          {currentIndex < posters.length - 1 && (
            <FaArrowRight className="arrow right" onClick={handleNextClick} />
          )}
        </div>
      )}
    </div>
  );
};

export default TopHome;
