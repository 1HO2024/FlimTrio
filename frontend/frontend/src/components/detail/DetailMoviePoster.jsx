import "../../style/detail/DetailMoviePoster.css";

const DetailMoviePoster = ({ posterUrl }) => {
  return (
    <div className="DetailMoviePoster">
      <img src={posterUrl} alt="Movie Poster" />
    </div>
  );
};

export default DetailMoviePoster;
