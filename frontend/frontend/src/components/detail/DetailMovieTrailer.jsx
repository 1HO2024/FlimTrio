import "../../style/detail/DetailMovieTrailer.css";
import useDetailMovieTrailer from "../../hooks/detail/useDetailMovieTrailer";

const DetailMovieTrailer = ({ movieName }) => {
  const { trailerUrl } = useDetailMovieTrailer({ movieName });

  return trailerUrl ? (
    <div className="DetailMovieTrailer">
      <iframe
        width="560"
        height="315"
        src={trailerUrl}
        frameBorder="0"
        allow="autoplay"
        allowFullScreen
        title="Movie Trailer"
      ></iframe>
    </div>
  ) : (
    <p style={{ color: "white" }}>예고편을 찾을 수 없습니다.</p>
  );
};

export default DetailMovieTrailer;
