import { useLocation } from "react-router-dom";
import DetailMoviePoster from "../../components/detail/DetailMoviePoster";
import DetailMovieInfo from "../../components/detail/DetailMovieInfo";
import DetailMovieReviews from "../../components/detail/DetailMovieReviews";
import DetailMovieTrailer from "../../components/detail/DetailMovieTrailer";
import "../../style/detail/DetailModal.css";

const DetailModal = ({ closeModal }) => {
  const location = useLocation();
  const { result } = location.state || {};

  return (
    <div className="DetailModalOverlay" onClick={closeModal}>
      <div className="DetailModal" onClick={(e) => e.stopPropagation()}>
        <div className="DetailPageMainContent">
          <div className="DetailPageMovieInfo">
            <DetailMovieInfo
              description="임시 영화 설명입니다. 실제 설명은 백엔드에서 받아옵니다."
              releaseDate="2025-01-01"
              genre="액션, 드라마"
            />
          </div>
          <div className="DetailPageMoviePoster">
            <DetailMoviePoster posterUrl="/images/Avengers.jpeg" />
          </div>
        </div>
        <div className="DetailPageBottomContent">
          <div className="DetailPageTrailer">
            <DetailMovieTrailer movieName={result?.name} />
          </div>
          <div className="DetailPageReviews">
            <DetailMovieReviews />
          </div>
        </div>
      </div>
    </div>
  );
};

export default DetailModal;
