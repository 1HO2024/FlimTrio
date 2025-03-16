import { useEffect, useState } from "react";
import "../../style/detail/DetailMovieTrailer.css";

const DetailMovieTrailer = ({ movieName }) => {
  const [trailerUrl, setTrailerUrl] = useState("");
  const youtubeApikey = import.meta.env.VITE_YOUTUBE_API_KEY;
  useEffect(() => {
    const fetchTrailer = async () => {
      const apiKey = youtubeApikey;
      const response = await fetch(
        `https://www.googleapis.com/youtube/v3/search?part=snippet&q=${movieName}+trailer&type=video&key=${apiKey}`
      );
      const data = await response.json();
      const videoId = data.items[0]?.id?.videoId;

      if (videoId) {
        setTrailerUrl(`https://www.youtube.com/embed/${videoId}`);
      }
    };

    if (movieName) {
      fetchTrailer();
    }
  }, [movieName]);

  return trailerUrl ? (
    <div className="DetailMovieTrailer">
      <iframe
        width="560"
        height="315"
        src={trailerUrl}
        frameBorder="0"
        allowFullScreen
        title="Movie Trailer"
      ></iframe>
    </div>
  ) : (
    <p>예고편을 찾을 수 없습니다.</p>
  );
};

export default DetailMovieTrailer;
