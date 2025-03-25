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
          <div className="DetailPageMoviePoster">
            <DetailMoviePoster posterUrl="/images/Avengers.jpeg" />
          </div>
          <div className="DetailPageTrailer">
            <DetailMovieTrailer movieName={result?.name} />
          </div>
        </div>

        <div className="DetailPageBottomContent">
          <div className="DetailPageMovieInfo">
            <DetailMovieInfo description="인피니티 워 이후 많은 사람이 죽고 또 많은 것을 잃게 된 지구는 더 이상 희망이 남지 않아 절망 속에 살아간다. 전쟁 후 남아 있던 어벤저스는 그런 그들의 모습을 보게 된다. 마지막으로 지구를 살리려 모든 것을 건 타노스와 최후의 전쟁을 치른다." />
          </div>
        </div>
        <div className="DetailPageReviews">
          <DetailMovieReviews />
        </div>
      </div>
    </div>
  );
};

export default DetailModal;
