import "../../style/detail/DetailMovieInfo.css";

const DetailMovieInfo = ({ description, releaseDate, genre }) => {
  return (
    <div className="DetailMovieInfo">
      <p>
        <strong>설명:</strong> {description}
      </p>
      <p>
        <strong>개봉일:</strong> {releaseDate}
      </p>
      <p>
        <strong>장르:</strong> {genre}
      </p>
    </div>
  );
};

export default DetailMovieInfo;
